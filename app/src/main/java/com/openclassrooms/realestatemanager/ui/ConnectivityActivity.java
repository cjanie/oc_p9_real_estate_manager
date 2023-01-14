package com.openclassrooms.realestatemanager.ui;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.openclassrooms.realestatemanager.ui.utils.GeocodingRequestWorker;
import com.openclassrooms.realestatemanager.ui.viewmodels.ConnectivityViewModel;

public class ConnectivityActivity extends BaseActivity {

    private ConnectivityViewModel connectivityViewModel;

    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.connectivityViewModel = new ViewModelProvider(this).get(ConnectivityViewModel.class);
        this.connectivityManager = getSystemService(ConnectivityManager.class);

        this.connectivityViewModel.isNetworkConnected().observe(this, isNetworkConnected -> {
            if(isNetworkConnected) {
                Toast.makeText(this, "network connected", Toast.LENGTH_LONG).show();
                WorkManager workManager = WorkManager.getInstance(this);
                workManager.enqueue(OneTimeWorkRequest.from(GeocodingRequestWorker.class));
                // TODO make workmanager doWork
                Log.i(this.getClass().getName(), "TODO Make workManager doWork()");
            } else {
                Toast.makeText(this, "network not connected", Toast.LENGTH_LONG).show();
            }
        });

        this.handleNetworkCallbackRegistration();
    }

    private void handleNetworkCallbackRegistration() {
        NetworkRequest networkRequest = this.getNetworkRequest();
        ConnectivityManager.NetworkCallback networkCallback = this.getNetworkCallback();

        this.getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                DefaultLifecycleObserver.super.onResume(owner);

                connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
            }

            @Override
            public void onPause(@NonNull LifecycleOwner owner) {
                DefaultLifecycleObserver.super.onPause(owner);
                connectivityManager.unregisterNetworkCallback(networkCallback);
            }
        });
    }

    private NetworkRequest getNetworkRequest() {
        // Configure the network request
        return new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();
    }

    private ConnectivityManager.NetworkCallback getNetworkCallback() {
        return new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                connectivityViewModel.updateIsNetworkConnected();
                // TODO Worker on available network

            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                makeToast(NetworkState.ON_LOST);
            }

            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
                makeToast(NetworkState.ON_CAPABILITIES_CHANGED);
            }
        };
    }

    private void makeToast(NetworkState networkState) {
        if (networkState.equals(NetworkState.ON_AVAILABLE)) {
            Toast.makeText(this, "available network", Toast.LENGTH_LONG).show();
        } else if (networkState.equals(NetworkState.ON_LOST)) {
            Toast.makeText(this, "network lost", Toast.LENGTH_LONG).show();
        } else if (networkState.equals(NetworkState.ON_CAPABILITIES_CHANGED)) {
            Toast.makeText(this, "network capabilities changed", Toast.LENGTH_LONG).show();

        }
    }

    private enum NetworkState {
        ON_AVAILABLE, ON_LOST, ON_CAPABILITIES_CHANGED
    }

}

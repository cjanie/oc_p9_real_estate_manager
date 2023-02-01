package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import static com.openclassrooms.realestatemanager.ui.utils.HttpConnectionConfig.CONNECT_TIME_OUT;
import static com.openclassrooms.realestatemanager.ui.utils.HttpConnectionConfig.PING_URL;
import static com.openclassrooms.realestatemanager.ui.utils.HttpConnectionConfig.READ_TIME_OUT;
import static com.openclassrooms.realestatemanager.ui.utils.HttpConnectionConfig.REQUEST_METHOD;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.NetworkGateway;
import com.openclassrooms.realestatemanager.ui.ConnectivityActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class NetworkGatewayImpl implements NetworkGateway {

    private BehaviorSubject<Boolean> isNetworkAvailable;

    private BehaviorSubject<Boolean> isNetworkConnected;

    private BehaviorSubject<String> message;

    public NetworkGatewayImpl() {
        this.isNetworkAvailable = BehaviorSubject.create();
        this.isNetworkConnected = BehaviorSubject.create();
        this.message = BehaviorSubject.create();
    }

    @Override
    public Observable<Boolean> isNetworkAvailable() {
        return this.isNetworkAvailable;
    }

    @Override
    public Observable<Boolean> isNetworkConnected() {
        return this.isNetworkConnected;
    }

    @Override
    public Observable<String> getMessage() {
        return this.message;
    }

    public void updateNetworkConnected(ConnectivityManager connectivityManager) {

    }

    public void updateNetworkConnected() throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(PING_URL).openConnection();
        httpURLConnection.setReadTimeout(READ_TIME_OUT);
        httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
        httpURLConnection.setRequestMethod(REQUEST_METHOD);
        httpURLConnection.connect();
        this.isNetworkConnected.onNext(httpURLConnection.getResponseCode() == 200);
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
                isNetworkAvailable.onNext(true);
                message.onNext("network is available");
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                isNetworkAvailable.onNext(false);
                message.onNext("network is lost");
            }

            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
                isNetworkAvailable.onNext(false);
                message.onNext("network capabilities changed");
            }
        };
    }

}

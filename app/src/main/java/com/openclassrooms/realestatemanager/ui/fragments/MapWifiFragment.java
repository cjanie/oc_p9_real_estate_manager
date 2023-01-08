package com.openclassrooms.realestatemanager.ui.fragments;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Launch;

import com.openclassrooms.realestatemanager.ui.utils.Utils;
import com.openclassrooms.realestatemanager.ui.viewmodels.GeolocationViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.GeolocationViewModelFactory;

public abstract class MapWifiFragment extends MapsFragment {

    protected GeolocationViewModel geolocationViewModel;

    private WifiManager wifiManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GeolocationViewModelFactory geolocationViewModelFactory = ((Launch)this.getActivity().getApplicationContext()).geolocationViewModelFactory();
        this.geolocationViewModel = new ViewModelProvider(this, geolocationViewModelFactory).get(GeolocationViewModel.class);

        this.wifiManager = (WifiManager) this.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    private boolean isWifiAvailable() {
        return Utils.isWifiEnabled(this.wifiManager);
    }

    private void setWifiEnabled(boolean enable) {
        Utils.setWifiEnabled(this.wifiManager, enable, this.getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        this.fetchGeolocationDataWhenWifiEnabled();
    }

    protected void checkWifiToFetchGeolocationData() {
        if(this.isWifiAvailable()) {
            this.fetchGeolocationDataWhenWifiEnabled();
        } else {
            setWifiEnabled(true);
        }
    }

    protected abstract void fetchGeolocationDataWhenWifiEnabled();
}

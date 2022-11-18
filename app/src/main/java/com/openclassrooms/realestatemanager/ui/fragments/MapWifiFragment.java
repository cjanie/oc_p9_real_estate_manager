package com.openclassrooms.realestatemanager.ui.fragments;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.exceptions.GeolocationException;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

public abstract class MapWifiFragment extends MapsFragment {

    private WifiManager wifiManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.wifiManager = (WifiManager) this.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    protected boolean isWifiAvailable() {
        return Utils.isWifiEnabled(this.wifiManager);
    }

    protected void setWifiEnabled(boolean enable) {
        Utils.setWifiEnabled(this.wifiManager, enable, this.getActivity());
    }

}

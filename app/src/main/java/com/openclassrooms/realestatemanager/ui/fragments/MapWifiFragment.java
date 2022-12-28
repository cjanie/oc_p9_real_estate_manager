package com.openclassrooms.realestatemanager.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.GeolocationException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.PayloadException;
import com.openclassrooms.realestatemanager.ui.utils.Utils;
import com.openclassrooms.realestatemanager.ui.viewmodels.GeolocationViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.GeolocationViewModelFactory;

import java.util.Arrays;

public abstract class MapWifiFragment extends MapsFragment {

    protected GeolocationViewModel geolocationViewModel;

    private WifiManager wifiManager;

    ActivityResultLauncher wifiLauncher = this.registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK) {

                Log.e(TAG, result.getResultCode() + " result code from wifi launcher");
            }

        }
    });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GeolocationViewModelFactory geolocationViewModelFactory = ((Launch)this.getActivity().getApplicationContext()).geolocationViewModelFactory();
        this.geolocationViewModel = new ViewModelProvider(this, geolocationViewModelFactory).get(GeolocationViewModel.class);

        this.wifiManager = (WifiManager) this.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.wifiLauncher.launch(new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY));
    }

    protected boolean isWifiAvailable() {
        return Utils.isWifiEnabled(this.wifiManager);
    }

    protected void setWifiEnabled(boolean enable) {
        Utils.setWifiEnabled(this.wifiManager, enable, this.getActivity());
    }



}

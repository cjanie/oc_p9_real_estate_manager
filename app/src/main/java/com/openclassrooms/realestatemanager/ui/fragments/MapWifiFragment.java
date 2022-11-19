package com.openclassrooms.realestatemanager.ui.fragments;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

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

    private final String api = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GeolocationViewModelFactory geolocationViewModelFactory = ((Launch)this.getActivity().getApplicationContext()).geolocationViewModelFactory();
        this.geolocationViewModel = new ViewModelProvider(this, geolocationViewModelFactory).get(GeolocationViewModel.class);

        this.wifiManager = (WifiManager) this.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    protected boolean isWifiAvailable() {
        return Utils.isWifiEnabled(this.wifiManager);
    }

    protected void setWifiEnabled(boolean enable) {
        Utils.setWifiEnabled(this.wifiManager, enable, this.getActivity());
    }

    protected Geolocation geolocalizeAddress(String streetNumberAndName, String location, String country) throws PayloadException, GeolocationException {
        if(streetNumberAndName != null && location != null && country != null) {

            GeoApiContext geoApiContext = new GeoApiContext();
            geoApiContext.setApiKey(BuildConfig.GOOGLE_PLACE_API_KEY);
            // Get latitude and longitude from address in api
            GeocodingApiRequest request = GeocodingApi.newRequest(geoApiContext)
                    .address(streetNumberAndName + " " + location + " " + country);

            try {

                Arrays.stream(request.await()).map(geocodingResult -> {
                    Geolocation geolocation = new Geolocation(
                            geocodingResult.geometry.location.lat,
                            geocodingResult.geometry.location.lng);
                    Log.d(this.getClass().getName(), "geolocation from api : " + geocodingResult.geometry.location.lat + " " + geocodingResult.geometry.location.lng);
                    return geolocation;
                });

            } catch (Exception e) {
                e.printStackTrace();
                throw new GeolocationException();
            }

        }
        throw new PayloadException();
    }



}

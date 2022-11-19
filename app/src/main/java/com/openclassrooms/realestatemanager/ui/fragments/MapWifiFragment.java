package com.openclassrooms.realestatemanager.ui.fragments;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.ui.exceptions.GeolocationException;
import com.openclassrooms.realestatemanager.ui.exceptions.PayloadException;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

import java.util.Arrays;

public abstract class MapWifiFragment extends MapsFragment {

    private WifiManager wifiManager;

    private final String api = "";

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

    protected Geolocation geolocalizeAddress(String streetNumberAndName, String location, String country) throws PayloadException, GeolocationException {
        if(streetNumberAndName != null && location != null && country != null) {

            GeoApiContext geoApiContext = new GeoApiContext();
            geoApiContext.setApiKey(BuildConfig.GOOGLE_PLACE_API_KEY);
            // Get latitude and longitude from address in api
            GeocodingApiRequest request = GeocodingApi.newRequest(geoApiContext)
                    .address(streetNumberAndName + " " + location + " " + country);

            try {
                request.await();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Arrays.stream(request.await()).map(geocodingResult -> {
                        Geolocation geolocation = new Geolocation(
                                geocodingResult.geometry.location.lat,
                                geocodingResult.geometry.location.lng);
                        return geolocation;
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new GeolocationException();
            }

        }
        throw new PayloadException();
    }



}

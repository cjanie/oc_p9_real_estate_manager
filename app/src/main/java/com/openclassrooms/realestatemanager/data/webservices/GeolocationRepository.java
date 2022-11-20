package com.openclassrooms.realestatemanager.data.webservices;

import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.data.webservices.mapapi.GoogleMapsRequestConfig;
import com.openclassrooms.realestatemanager.data.webservices.mapapi.deserializers.GeolocationResponseRoot;
import com.openclassrooms.realestatemanager.data.webservices.mapapi.deserializers.Result;
import com.openclassrooms.realestatemanager.data.webservices.retrofit.GeolocationRequest;
import com.openclassrooms.realestatemanager.data.webservices.retrofit.GoogleMapsHttpClientProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GeolocationRepository {

    private GeolocationRequest geolocationRequest;

    public GeolocationRepository(GoogleMapsHttpClientProvider httpClientProvider) {
        this.geolocationRequest = httpClientProvider.getRetrofit().create(GeolocationRequest.class);
    }

    private Observable<GeolocationResponseRoot> getGeolocationFromAddressResponseRoot(String address) {
        return this.geolocationRequest.getData(address, GoogleMapsRequestConfig.API_KEY);
    }

    public Observable<List<Geolocation>> getGeolocationFromAddress(String address) {
        String formatedAddress = address.replace(" ", "%20");
        return this.getGeolocationFromAddressResponseRoot(formatedAddress).map(response -> {
            List<Geolocation> geolocations = new ArrayList<>();
            if(!response.getResults().isEmpty()) {
                for(Result r: response.getResults()) {
                    Geolocation geolocation = new Geolocation(
                            r.getGeometry().getLocation().getLat(),
                            r.getGeometry().getLocation().getLng()
                    );
                    geolocations.add(geolocation);
                }
            }
            return geolocations;
        });
    }
}

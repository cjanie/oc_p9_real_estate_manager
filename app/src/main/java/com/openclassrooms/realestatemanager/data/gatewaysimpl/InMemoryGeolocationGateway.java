package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.GeolocationException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.GeolocationGateway;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class InMemoryGeolocationGateway implements GeolocationGateway {

    @Override
    public Observable<List<Geolocation>> geolocalize(String streetNumberAndName, String location, String country) {
        return Observable.just(Arrays.asList(new Geolocation(0.0, 0.0)));
    }
}

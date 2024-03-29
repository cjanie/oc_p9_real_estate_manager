package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.GeolocationGateway;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.GeolocationException;
import com.openclassrooms.realestatemanager.data.webservices.GeolocationRepository;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GeolocationGatewayImpl implements GeolocationGateway {

    private GeolocationRepository geolocationRepository;

    public GeolocationGatewayImpl(GeolocationRepository geolocationRepository) {
        this.geolocationRepository = geolocationRepository;
    }

    @Override
    public Observable<List<Geolocation>> geolocalize(String streetNumberAndName, String location, String country) {
        return this.geolocationRepository.getGeolocationFromAddress(streetNumberAndName, location, country);
    }

}

package com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.GeolocationException;

import java.util.List;

import io.reactivex.Observable;

public interface GeolocationGateway {
    Observable<List<Geolocation>> geolocalize(String streetNumberAndName, String location, String country);
}

package com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways;

import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.ui.exceptions.GeolocationException;

public interface GeolocationGateway {
    Geolocation geolocalize(String address) throws GeolocationException;
}

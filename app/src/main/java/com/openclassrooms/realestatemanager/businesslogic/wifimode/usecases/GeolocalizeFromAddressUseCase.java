package com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases;

import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.GeolocationGateway;
import com.openclassrooms.realestatemanager.ui.exceptions.GeolocationException;

public class GeolocalizeFromAddressUseCase {

    GeolocationGateway geolocationGateway;

    public GeolocalizeFromAddressUseCase(GeolocationGateway geolocationGateway) {
        this.geolocationGateway = geolocationGateway;
    }

    public Geolocation handle(String address) throws GeolocationException {
        return this.geolocationGateway.geolocalize(address);
    }
}

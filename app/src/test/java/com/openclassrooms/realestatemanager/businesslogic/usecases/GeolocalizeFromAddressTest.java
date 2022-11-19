package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.GeolocationGateway;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases.GeolocalizeFromAddressUseCase;
import com.openclassrooms.realestatemanager.ui.exceptions.GeolocationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InMemoryGeolocationGateway implements GeolocationGateway {

    @Override
    public Geolocation geolocalize(String address) {
        return new Geolocation(0.0, 0.0);
    }
}

public class GeolocalizeFromAddressTest {

    @Test
    public void returnsLatitudeAndLongitude() throws GeolocationException {
        InMemoryGeolocationGateway geolocationGateway = new InMemoryGeolocationGateway();
        GeolocalizeFromAddressUseCase geolocalizeUseCase = new GeolocalizeFromAddressUseCase(geolocationGateway);
        Geolocation geolocation = geolocalizeUseCase.handle("2 passage Lonjon Montpellier France");
        Assertions.assertNotNull(geolocation.getLatitude());
        Assertions.assertNotNull(geolocation.getLongitude());
    }

}

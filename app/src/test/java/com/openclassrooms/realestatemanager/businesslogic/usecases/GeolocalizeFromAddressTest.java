package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.PayloadException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.GeolocationGateway;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases.GeolocalizeFromAddressUseCase;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.GeolocationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class InMemoryGeolocationGateway implements GeolocationGateway {

    @Override
    public Geolocation geolocalize(String address) {
        return new Geolocation(0.0, 0.0);
    }
}

public class GeolocalizeFromAddressTest {

    @Test
    public void returnsLatitudeAndLongitude() throws GeolocationException, PayloadException {
        InMemoryGeolocationGateway geolocationGateway = new InMemoryGeolocationGateway();
        GeolocalizeFromAddressUseCase geolocalizeUseCase = new GeolocalizeFromAddressUseCase(geolocationGateway);
        Geolocation geolocation = geolocalizeUseCase.handle("2 passage Lonjon Montpellier France");
        Assertions.assertNotNull(geolocation.getLatitude());
        Assertions.assertNotNull(geolocation.getLongitude());
    }

    @Test
    public void addressShouldBeDefined() {
        InMemoryGeolocationGateway geolocationGateway = new InMemoryGeolocationGateway();
        GeolocalizeFromAddressUseCase geolocalizeUseCase = new GeolocalizeFromAddressUseCase(geolocationGateway);
        Assertions.assertThrows(PayloadException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                geolocalizeUseCase.handle(null);
            }
        });
    }
}

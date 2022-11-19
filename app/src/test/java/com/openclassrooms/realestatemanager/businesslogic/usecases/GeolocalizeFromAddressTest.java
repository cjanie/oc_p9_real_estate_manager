package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GeolocalizeFromAddressUseCase {
    Geolocation handle(String address) {
        return new Geolocation(0.0, 0.0);
    }

}

public class GeolocalizeFromAddressTest {

    @Test
    public void returnsLatitude() {
        GeolocalizeFromAddressUseCase geolocalizeUseCase = new GeolocalizeFromAddressUseCase();
        Geolocation geolocation = geolocalizeUseCase.handle("2 passage Lonjon Montpellier France");

    }
}

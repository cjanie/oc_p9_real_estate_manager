package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.PayloadException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.GeolocationGateway;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases.GeolocalizeFromAddressUseCase;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.GeolocationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

class InMemoryGeolocationGateway implements GeolocationGateway {

    @Override
    public Observable<List<Geolocation>> geolocalize(String address) {
        return Observable.just(Arrays.asList(new Geolocation(0.0, 0.0)));
    }
}

public class GeolocalizeFromAddressTest {

    @Test
    public void returnsLatitudeAndLongitude() throws GeolocationException, PayloadException {
        InMemoryGeolocationGateway geolocationGateway = new InMemoryGeolocationGateway();
        GeolocalizeFromAddressUseCase geolocalizeUseCase = new GeolocalizeFromAddressUseCase(geolocationGateway);
        List<Geolocation> results = new ArrayList<>();
        Estate estate = new Estate();
        estate.setStreetNumberAndStreetName("2 passage Lonjon");
        estate.setLocation("Montpellier");
        estate.setCountry("France");
        geolocalizeUseCase.handle(estate).subscribe(results::addAll);
        Assertions.assertNotNull(results.get(0).getLatitude());
        Assertions.assertNotNull(results.get(0).getLongitude());
    }

    @Test
    public void addressShouldBeDefined() {
        InMemoryGeolocationGateway geolocationGateway = new InMemoryGeolocationGateway();
        GeolocalizeFromAddressUseCase geolocalizeUseCase = new GeolocalizeFromAddressUseCase(geolocationGateway);
        Assertions.assertThrows(PayloadException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                geolocalizeUseCase.handle((Estate) null);
            }
        });
    }

    @Test
    public void geolocationsToEstates() throws PayloadException {
        InMemoryGeolocationGateway geolocationGateway = new InMemoryGeolocationGateway();
        GeolocalizeFromAddressUseCase geolocalizeUseCase = new GeolocalizeFromAddressUseCase(geolocationGateway);
        Estate estate1 = new Estate();
        estate1.setStreetNumberAndStreetName("2 passage Lonjon");
        estate1.setLocation("Montpellier");
        estate1.setCountry("France");
        List<Estate> estates = Arrays.asList(estate1);

        List<Estate> results = new ArrayList<>();
        geolocalizeUseCase.handle(estates).subscribe(results::addAll);
        Assertions.assertNotNull(results.get(0).getLatitude());
        Assertions.assertNotNull(results.get(0).getLongitude());
    }


}

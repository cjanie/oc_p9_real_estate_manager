package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.PayloadException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.GeolocationGateway;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases.GeolocalizeFromAddressUseCase;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.GeolocationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

class InMemoryGeolocationGateway implements GeolocationGateway {

    @Override
    public Observable<List<Geolocation>> geolocalize(String streetNumberAndName, String location, String country) throws GeolocationException {
        return Observable.just(Arrays.asList(new Geolocation(0.0, 0.0)));
    }
}

public class GeolocalizeFromAddressTest {

    @Test
    public void returnsLatitudeAndLongitudeOfEstates() throws PayloadException {
        InMemoryGeolocationGateway geolocationGateway = new InMemoryGeolocationGateway();
        GeolocalizeFromAddressUseCase geolocalizeUseCase = new GeolocalizeFromAddressUseCase(geolocationGateway);

        List<Estate> results = new ArrayList<>();

        Estate estate = new Estate();
        estate.setStreetNumberAndStreetName("2 passage Lonjon");
        estate.setLocation("Montpellier");
        estate.setCountry("France");

        Observable<Estate> observableFromIterable = Observable.fromIterable(Arrays.asList(estate));
        geolocalizeUseCase.handleList(observableFromIterable).subscribe(results::addAll);
        Assertions.assertEquals(1, results.size());
        Assertions.assertNotNull(results.get(0).getLatitude());
        Assertions.assertNotNull(results.get(0).getLongitude());
    }

    @Test
    public void returnsLatitudeAndLongitudeOfEstate() throws PayloadException, GeolocationException {
        InMemoryGeolocationGateway geolocationGateway = new InMemoryGeolocationGateway();
        GeolocalizeFromAddressUseCase geolocalizeUseCase = new GeolocalizeFromAddressUseCase(geolocationGateway);
        Estate estate = new Estate();
        estate.setStreetNumberAndStreetName("2 passage Lonjon");
        estate.setLocation("Montpellier");
        estate.setCountry("France");

        List<Estate> results = new ArrayList<>();

        geolocalizeUseCase.handleOne(estate).subscribe(results::add);

        Assertions.assertNotNull(results.get(0).getLatitude());
        Assertions.assertNotNull(results.get(0).getLongitude());
    }


}

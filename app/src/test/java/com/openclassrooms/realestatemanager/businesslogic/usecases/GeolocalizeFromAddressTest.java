package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.NetworkException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases.GeolocalizeFromAddressUseCase;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryGeolocationGateway;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryNetworkGateway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class GeolocalizeFromAddressTest {

    private GeolocalizeFromAddressUseCase createUseCase(boolean isNetworkConnected) {
        InMemoryGeolocationGateway geolocationGateway = new InMemoryGeolocationGateway();
        InMemoryNetworkGateway networkGateway = new InMemoryNetworkGateway();
        networkGateway.setNetworkConnected(isNetworkConnected);
        return new GeolocalizeFromAddressUseCase(geolocationGateway, networkGateway);
    }

    private Estate createEstateWithAddress() {
        Estate estate = new Estate();
        estate.setStreetNumberAndStreetName("2 passage Lonjon");
        estate.setLocation("Montpellier");
        estate.setCountry("France");
        return estate;
    }

    @Test
    public void returnsLatitudeAndLongitudeOfEstates() {
        GeolocalizeFromAddressUseCase geolocalizeUseCase = this.createUseCase(true);
        List<Estate> results = new ArrayList<>();

        Estate estate = this.createEstateWithAddress();

        Observable<Estate> observableFromIterable = Observable.fromIterable(Arrays.asList(estate));
        geolocalizeUseCase.handleList(observableFromIterable).subscribe(results::addAll);
        Assertions.assertEquals(1, results.size());
        Assertions.assertNotNull(results.get(0).getLatitude());
        Assertions.assertNotNull(results.get(0).getLongitude());
    }

    @Test
    public void returnsLatitudeAndLongitudeOfEstate() {
        GeolocalizeFromAddressUseCase geolocalizeUseCase = this.createUseCase(true);

        Estate estate = this.createEstateWithAddress();

        List<Estate> results = new ArrayList<>();

        geolocalizeUseCase.handleOne(estate).subscribe(results::add);

        Assertions.assertNotNull(results.get(0).getLatitude());
        Assertions.assertNotNull(results.get(0).getLongitude());
    }

    @Test
    public void requestsNetwork() {
        GeolocalizeFromAddressUseCase geolocaliseUseCase = this.createUseCase(false);
        Assertions.assertThrows(NetworkException.class, () -> {
           geolocaliseUseCase.handleOne(this.createEstateWithAddress());
        });
    }
}

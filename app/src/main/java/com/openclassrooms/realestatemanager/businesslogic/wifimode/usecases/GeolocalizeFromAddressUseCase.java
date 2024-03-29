package com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases;

import android.util.Log;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.NetworkException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.PayloadException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.GeolocationGateway;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.GeolocationException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.NetworkGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class GeolocalizeFromAddressUseCase {

    GeolocationGateway geolocationGateway;

    NetworkGateway networkGateway;

    public GeolocalizeFromAddressUseCase(GeolocationGateway geolocationGateway, NetworkGateway networkGateway) {
        this.geolocationGateway = geolocationGateway;
        this.networkGateway = networkGateway;
    }

    public Observable<Estate> handleOne(Estate estate) {

        return this.geolocationGateway.geolocalize(estate.getStreetNumberAndStreetName(), estate.getLocation(), estate.getCountry())
                .map(geolocations -> {
                    estate.setLatitude(geolocations.get(0).getLatitude());
                    estate.setLongitude(geolocations.get(0).getLongitude());
                    return estate;
                });


    }

    public Observable<List<Geolocation>> getGeolocationResults(Estate estate) {
        // if !internet Observable.error(new Exception());
        // stocke var internet depuis view model
        return this.geolocationGateway.geolocalize(estate.getStreetNumberAndStreetName(), estate.getLocation(), estate.getCountry());
    }

    public Observable<List<Estate>> handleList(Observable<Estate> observableFromIterable) {

        return observableFromIterable
                .doOnError(throwable -> Log.e(this.getClass().getName(), "fetchGeolocationsToUpdateLiveData doOnError : " + throwable.getClass().getName()))

                .flatMap(estate -> this.geolocalize(estate)
                        .map(geolocations -> {
                            if(!geolocations.isEmpty()) {
                                estate.setLatitude(geolocations.get(0).getLatitude());
                                estate.setLongitude(geolocations.get(0).getLongitude());
                                return estate;
                            } else {
                                throw new GeolocationException();
                            }
                        }))
                .toList()
                .toObservable();
    }

    private void checkPayload(Estate estate) throws PayloadException {
        if(estate == null) {
            throw new PayloadException();
        } else {
            if(estate.getStreetNumberAndStreetName() == null || estate.getLocation() == null || estate.getCountry() == null) {
                throw new PayloadException();
            }
        }
    }

    private Observable<List<Geolocation>> geolocalize(Estate estate) throws PayloadException {
        this.checkPayload(estate);
        return this.geolocationGateway.geolocalize(estate.getStreetNumberAndStreetName(), estate.getLocation(), estate.getCountry());
    }

}

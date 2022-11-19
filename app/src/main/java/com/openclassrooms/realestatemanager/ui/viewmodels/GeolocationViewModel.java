package com.openclassrooms.realestatemanager.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.PayloadException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases.GeolocalizeFromAddressUseCase;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.GeolocationException;

import java.util.ArrayList;
import java.util.List;

public class GeolocationViewModel extends ViewModel {

    private GeolocalizeFromAddressUseCase geolocalizeFromAddressUseCase;

    private MutableLiveData<List<Estate>> geolocalizedEstates;

    public GeolocationViewModel(GeolocalizeFromAddressUseCase geolocalizeFromAddressUseCase) {
        this.geolocalizeFromAddressUseCase = geolocalizeFromAddressUseCase;

        this.geolocalizedEstates = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Estate>> getGeolocalizedEstates() {
        return geolocalizedEstates;
    }

    // action
    public void fetchGeolocationsToUpdateLiveData(List<Estate> ungeolocalizedEstates) throws GeolocationException, PayloadException {
        List<Estate> geolocalized = new ArrayList<>();
        if(!ungeolocalizedEstates.isEmpty()) {
            for(Estate estate: ungeolocalizedEstates) {
                Geolocation geolocation = null;
                try {
                    geolocation = this.geolocalizeFromAddressUseCase.handle(estate.getStreetNumberAndStreetName() + " " + estate.getLocation() + " " + estate.getCountry());
                    estate.setLatitude(geolocation.getLatitude());
                    estate.setLongitude(geolocation.getLongitude());
                    geolocalized.add(estate);
                } catch (GeolocationException | PayloadException e) {
                    throw e;
                }
            }
        }
        this.geolocalizedEstates.postValue(geolocalized);
    }
}

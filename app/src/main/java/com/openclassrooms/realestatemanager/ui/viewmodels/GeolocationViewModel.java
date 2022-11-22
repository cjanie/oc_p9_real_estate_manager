package com.openclassrooms.realestatemanager.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.GeolocationException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.PayloadException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases.GeolocalizeFromAddressUseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GeolocationViewModel extends ViewModel {

    private GeolocalizeFromAddressUseCase geolocalizeFromAddressUseCase;

    // For list
    private MutableLiveData<List<Estate>> geolocalizedEstates;

    // For one
    private MutableLiveData<Estate> geolocalisedEstate;

    public GeolocationViewModel(GeolocalizeFromAddressUseCase geolocalizeFromAddressUseCase) {
        this.geolocalizeFromAddressUseCase = geolocalizeFromAddressUseCase;

        this.geolocalizedEstates = new MutableLiveData<>(new ArrayList<>());
        this.geolocalisedEstate = new MutableLiveData<>();
    }

    // Getters to observe result
    public LiveData<List<Estate>> getGeolocalizedEstates() {
        return this.geolocalizedEstates;
    }

    public LiveData<Estate> getGeolocalizedEstate() {
        return this.geolocalisedEstate;
    }

    // Action for list
    public void fetchGeolocationsToUpdateLiveData(List<Estate> ungeolocalizedEstates) throws PayloadException {
        if(!ungeolocalizedEstates.isEmpty()) {
            Observable<Estate> observableFromIterable = Observable.fromIterable(ungeolocalizedEstates)
                    .observeOn(Schedulers.io());
            this.geolocalizeFromAddressUseCase.handleList(observableFromIterable)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            estates -> this.geolocalizedEstates.postValue(estates),
                            error -> Log.e(this.getClass().getName(), "fetchGeolocationsToUpdateLiveData subscribe error : " + error.getClass().getName())
                    );
        }
    }

    // Action for one
    public void fetchGeolocationsToUpdateLiveData(Estate ungeolocalizedEstate) throws GeolocationException {
        this.geolocalizeFromAddressUseCase.handleOne(ungeolocalizedEstate)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        estate -> this.geolocalisedEstate.postValue(estate),
                        error -> Log.e(this.getClass().getName(), "fetchGeolocationsToUpdateLiveData subscribe error : " + error.getClass().getName())
                );
    }
}

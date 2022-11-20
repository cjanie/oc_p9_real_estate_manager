package com.openclassrooms.realestatemanager.ui.viewmodels;

import android.util.Log;
import android.widget.Toast;

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

import io.reactivex.schedulers.Schedulers;

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
    public void fetchGeolocationsToUpdateLiveData(List<Estate> ungeolocalizedEstates) throws PayloadException {
        if(!ungeolocalizedEstates.isEmpty()) {
            this.geolocalizeFromAddressUseCase.handle(ungeolocalizedEstates)
                    .subscribe(
                            estates -> this.geolocalizedEstates.postValue(estates),
                            error -> Log.e(this.getClass().getName(), "fetchGeolocationsToUpdateLiveData subscribe error : " + error.getClass().getName())
                    );
        }
    }
}

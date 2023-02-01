package com.openclassrooms.realestatemanager.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases.GeolocalizeFromAddressUseCase;
import com.openclassrooms.realestatemanager.ui.viewmodels.GeocodingViewModel;

public class GeocodingViewModelFactory implements ViewModelProvider.Factory {

    private final GeolocalizeFromAddressUseCase geolocalizeFromAddressUseCase;

    public GeocodingViewModelFactory(GeolocalizeFromAddressUseCase geolocalizeFromAddressUseCase) {
        this.geolocalizeFromAddressUseCase = geolocalizeFromAddressUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(GeocodingViewModel.class)) {
            return (T) new GeocodingViewModel(this.geolocalizeFromAddressUseCase);
        }
        throw new IllegalArgumentException("geocoding view model factory");
    }
}

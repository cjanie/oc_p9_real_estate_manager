package com.openclassrooms.realestatemanager.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases.GeolocalizeFromAddressUseCase;
import com.openclassrooms.realestatemanager.ui.viewmodels.FormViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.GeolocationViewModel;

import org.jetbrains.annotations.NotNull;

public class GeolocationViewModelFactory implements ViewModelProvider.Factory {

    private final GeolocalizeFromAddressUseCase geolocalizeFromAddressUseCase;

    public GeolocationViewModelFactory(GeolocalizeFromAddressUseCase geolocalizeFromAddressUseCase) {
        this.geolocalizeFromAddressUseCase = geolocalizeFromAddressUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(GeolocationViewModel.class)) {
            return (T) new GeolocationViewModel(this.geolocalizeFromAddressUseCase);
        }
        throw new IllegalArgumentException("geolocation view model factory");
    }
}

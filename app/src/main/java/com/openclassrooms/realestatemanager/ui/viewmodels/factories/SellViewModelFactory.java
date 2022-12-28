package com.openclassrooms.realestatemanager.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.businesslogic.usecases.SaveEstateUseCase;
import com.openclassrooms.realestatemanager.businesslogic.usecases.SellEstateUseCase;
import com.openclassrooms.realestatemanager.ui.viewmodels.SellViewModel;

public class SellViewModelFactory implements ViewModelProvider.Factory {

    private SellEstateUseCase sellEstateUseCase;

    public SellViewModelFactory(SellEstateUseCase sellEstateUseCase) {
        this.sellEstateUseCase = sellEstateUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SellViewModel.class)) {
            return (T) new SellViewModel(this.sellEstateUseCase);
        }
        throw new IllegalArgumentException("sell view model factory");
    }

}

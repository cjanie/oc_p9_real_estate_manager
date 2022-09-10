package com.openclassrooms.realestatemanager.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.businesslogic.usecases.SaveEstateUseCase;
import com.openclassrooms.realestatemanager.ui.viewmodels.FormViewModel;

public class FormViewModelFactory implements ViewModelProvider.Factory {

    private SaveEstateUseCase saveEstateUseCase;

    public FormViewModelFactory(SaveEstateUseCase saveEstateUseCase) {
        this.saveEstateUseCase = saveEstateUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(FormViewModel.class)) {
            return (T) new FormViewModel(this.saveEstateUseCase);
        }
        throw new IllegalArgumentException("form view model factory");
    }

}

package com.openclassrooms.realestatemanager.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.businesslogic.usecases.GetEstatesUseCase;
import com.openclassrooms.realestatemanager.ui.viewmodels.EstatesViewModel;

public class EstatesViewModelFactory implements ViewModelProvider.Factory {

    private GetEstatesUseCase getEstatesUseCase;

    public EstatesViewModelFactory(GetEstatesUseCase getEstatesUseCase) {
        this.getEstatesUseCase = getEstatesUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(EstatesViewModel.class)) {
            return (T) new EstatesViewModel(this.getEstatesUseCase);
        }
        throw new IllegalArgumentException("estates view model factory");
    }
}

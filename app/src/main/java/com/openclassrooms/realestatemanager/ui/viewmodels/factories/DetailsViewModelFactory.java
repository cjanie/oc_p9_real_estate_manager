package com.openclassrooms.realestatemanager.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.businesslogic.usecases.GetEstateByIdUseCase;
import com.openclassrooms.realestatemanager.ui.viewmodels.DetailsViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.EstatesViewModel;

public class DetailsViewModelFactory implements ViewModelProvider.Factory {

    private final GetEstateByIdUseCase getEstateByIdUseCase;

    public DetailsViewModelFactory(GetEstateByIdUseCase getEstateByIdUseCase) {
        this.getEstateByIdUseCase = getEstateByIdUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(DetailsViewModel.class)) {
            return (T) new DetailsViewModel(this.getEstateByIdUseCase);
        }
        throw new IllegalArgumentException("details view model factory");
    }
}

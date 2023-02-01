package com.openclassrooms.realestatemanager.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.usecases.GetEstatesUseCase;

import java.util.ArrayList;
import java.util.List;

public class EstatesViewModel extends ViewModel {

    private GetEstatesUseCase getEstatesUseCase;

    private MutableLiveData<List<Estate>> estates;

    public EstatesViewModel(GetEstatesUseCase getEstatesUseCase) {
        this.getEstatesUseCase = getEstatesUseCase;
        this.estates = new MutableLiveData<>(new ArrayList<>());
    }

    public MutableLiveData<List<Estate>> getEstates() {
        return this.estates;
    }

    public void fetchEstatesToUpdateLiveData() {
        this.estates.postValue(this.getEstatesUseCase.handle());
    }
}

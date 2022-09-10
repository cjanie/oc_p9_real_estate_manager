package com.openclassrooms.realestatemanager.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.usecases.SaveEstateUseCase;

public class FormViewModel extends ViewModel {

    private SaveEstateUseCase saveEstateUseCase;

    public FormViewModel(SaveEstateUseCase saveEstateUseCase) {
        this.saveEstateUseCase = saveEstateUseCase;
    }

    public Long saveEstate(Estate estate) {
        return this.saveEstateUseCase.handle(estate);
    }


}

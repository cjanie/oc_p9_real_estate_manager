package com.openclassrooms.realestatemanager.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;

import com.openclassrooms.realestatemanager.businesslogic.usecases.SellEstateUseCase;

public class SellViewModel extends ViewModel {

    private SellEstateUseCase sellEstateUseCase;

    public SellViewModel(SellEstateUseCase sellEstateUseCase) {
        this.sellEstateUseCase = sellEstateUseCase;
    }

    public Long sell(Estate estate, String agentName) {
        return this.sellEstateUseCase.handle(estate, agentName);
    }
}

package com.openclassrooms.realestatemanager.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.usecases.GetEstatesUseCase;

import java.util.List;

public class EstatesViewModel extends ViewModel {



    private GetEstatesUseCase getEstatesUseCase;

    public EstatesViewModel(GetEstatesUseCase getEstatesUseCase) {
        this.getEstatesUseCase = getEstatesUseCase;
    }

    public List<Estate> getEstates() {

        System.out.println("estates size" + this.getEstatesUseCase.handle().size());
        return this.getEstatesUseCase.handle();
    }
}

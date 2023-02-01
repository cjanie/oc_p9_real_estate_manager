package com.openclassrooms.realestatemanager.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.usecases.GetEstateByIdUseCase;

public class DetailsViewModel extends ViewModel {

    private final GetEstateByIdUseCase getEstateByIdUseCase;

    private final MutableLiveData<Estate> estate;

    public DetailsViewModel(GetEstateByIdUseCase getEstateByIdUseCase) {
        this.getEstateByIdUseCase = getEstateByIdUseCase;

        this.estate = new MutableLiveData<>();
    }

    public LiveData<Estate> getEstate() {
        return this.estate;
    }

    public void fetchEstateToUpdateLiveData(int id) {
        Estate found = this.getEstateByIdUseCase.handle(id);
        this.estate.postValue(found);
    }
}

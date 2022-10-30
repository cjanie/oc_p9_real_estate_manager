package com.openclassrooms.realestatemanager.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.usecases.SaveEstateUseCase;

public class FormViewModel extends ViewModel {

    private SaveEstateUseCase saveEstateUseCase;

    private Estate estateData;

    public FormViewModel(SaveEstateUseCase saveEstateUseCase) {
        this.saveEstateUseCase = saveEstateUseCase;
    }

    public void setEstateData(Estate estate) {
        this.estateData = estate;
    }

    public Estate getEstateData() {
        return this.estateData;
    }

    public Long saveEstate(Estate estate) {
        return this.saveEstateUseCase.handle(estate);
    }

    public void setEstateDataAddress(String streetNumberAndNAme, String addressComplements, String zipCode, String country) {
        this.estateData.setStreetNumberAndStreetName(streetNumberAndNAme);
        this.estateData.setAddressComplements(addressComplements);
        this.estateData.setZipCode(zipCode);
        this.estateData.setCountry(country);
    }

    public void setEstateDataDescriptionDetails(
            Integer surface,
            Integer numberOfRooms,
            Integer numberOfBathromms,
            Integer numberOfBedrooms) {
        this.estateData.setSurface(surface);
        this.estateData.setNumberOfRooms(numberOfRooms);
        this.estateData.setNumberOfBathrooms(numberOfBathromms);
        this.estateData.setNumberOfBedrooms(numberOfBedrooms);
    }

    public Long saveEstateDataUpdate() {
        return this.saveEstateUseCase.handle(this.estateData);
    }
}

package com.openclassrooms.realestatemanager.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.entities.Media;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.businesslogic.usecases.SaveEstateUseCase;

import java.util.List;

public class FormViewModel extends ViewModel {

    private SaveEstateUseCase saveEstateUseCase;

    private Estate estateData;

    public FormViewModel(SaveEstateUseCase saveEstateUseCase) {
        this.saveEstateUseCase = saveEstateUseCase;
        this.estateData = new Estate();
    }

    public void setEstateData(Estate estate) {
        this.estateData = estate;
    }

    public Estate getEstateData() {
        return this.estateData;
    }

    public void setEstateDataMandatory(
            EstateType type,
            String location,
            Float price,
            Devise devise) {
        this.estateData.setType(type);
        this.estateData.setLocation(location);
        this.estateData.setPrice(price);
        this.estateData.setDevise(devise);
    }

    public void setEstateDataAddress(
            String streetNumberAndNAme,
            String addressComplements,
            String zipCode,
            String country
    ) {
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

    public void setEstateDataDescription(String description) {
        this.estateData.setDescription(description);
    }

    public void setEstateDataMedia(List<Media> media) {
        this.estateData.setMedia(media);
    }

    public void setEstateDataGeolocation(Double latitude, Double longitude) {
        this.estateData.setLatitude(latitude);
        this.estateData.setLongitude(longitude);
    }

    public Long saveEstateDataUpdate() {
        Long id = this.saveEstateUseCase.handle(this.estateData);
        this.estateData.setId(Integer.parseInt(id.toString()));
        return id;
    }
}

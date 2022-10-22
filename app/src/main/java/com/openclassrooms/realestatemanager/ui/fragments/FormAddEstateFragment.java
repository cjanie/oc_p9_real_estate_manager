package com.openclassrooms.realestatemanager.ui.fragments;

import android.text.TextUtils;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.ui.Action;

public class FormAddEstateFragment extends FormFragment {

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected Long save() {
        Estate estate = new Estate();
        estate.setType(this.getEstateType());
        if(!TextUtils.isEmpty(this.location.getText())
                && !TextUtils.isEmpty(this.price.getText())) {
            estate.setLocation(this.location.getText().toString());
            estate.setStreetNumberAndStreetName(this.streetNumberAndStreetName.toString());
            estate.setAddressComplements(this.addressComplements.toString());
            estate.setZipCode(this.zipCode.toString());
            estate.setCountry(this.country.toString());
            try {
                estate.setPrice(Float.parseFloat(this.price.getText().toString()));
                estate.setDevise(Devise.DOLLAR);
                if(!TextUtils.isEmpty(this.surface.getText())) {
                    estate.setSurface(Integer.parseInt(this.surface.getText().toString()));
                }
                if(!TextUtils.isEmpty(this.numberOfRooms.getText())) {
                    estate.setNumberOfRooms(Integer.parseInt(this.numberOfRooms.getText().toString()));
                }
                if(!TextUtils.isEmpty(this.numberOfBathrooms.getText())) {
                    estate.setNumberOfBathrooms(Integer.parseInt(this.numberOfBathrooms.getText().toString()));
                }
                if(!TextUtils.isEmpty(this.numberOfBedrooms.getText())) {
                    estate.setNumberOfBedrooms(Integer.parseInt(this.numberOfBedrooms.getText().toString()));
                }

                return this.formViewModel.saveEstate(estate);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return 0L;

        } else {
            return 0L;
        }
    }
}

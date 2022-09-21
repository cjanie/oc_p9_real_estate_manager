package com.openclassrooms.realestatemanager.ui.fragments;

import android.text.TextUtils;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;

public class FormAddEstateFragment extends FormFragment {
    @Override
    protected Long save() {
        Estate estate = new Estate();
        estate.setType(this.getEstateType());
        if(!TextUtils.isEmpty(this.location.getText())
                && !TextUtils.isEmpty(this.price.getText())) {
            estate.setLocation(this.location.getText().toString());
            try {
                estate.setPrice(Float.parseFloat(this.price.getText().toString()));
                estate.setDevise(Devise.DOLLAR);
                estate.setSurface(1000);
                estate.setNumberOfRooms(3);
                estate.setNumberOfBathrooms(1);
                estate.setNumberOfBedrooms(2);
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

package com.openclassrooms.realestatemanager.ui.fragments;

import android.text.TextUtils;
import android.view.View;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.ui.exceptions.IncorrectEstateTypeException;
import com.openclassrooms.realestatemanager.ui.exceptions.MandatoryException;

public class FormAddEstateFragment extends FormFragment {

    @Override
    protected Long save() {

        Estate estate = new Estate();

        String mandatoryFieldError = this.getString(R.string.required);

        Long id = 0L;

        // Type
        try {
            estate.setType(this.getEstateType());
        } catch (MandatoryException e) {
            this.type.setError(mandatoryFieldError);
        }

        // Location
        if(!TextUtils.isEmpty(this.location.getText())) {
            estate.setLocation(this.location.getText().toString());
        } else {
            this.location.setError(mandatoryFieldError);
        }

        // Price
        if(!TextUtils.isEmpty(this.price.getText())) {
            try {
                estate.setPrice(Float.parseFloat(this.price.getText().toString()));
            } catch (NumberFormatException e) {
                String nanError = this.getString(R.string.nan);
                this.price.setError(nanError);
            }
        } else {
            this.price.setError(mandatoryFieldError);
        }

        // Devise
        estate.setDevise(Devise.DOLLAR); // TODO Autocomplete field

        if(estate.getType() != null
                && estate.getLocation() != null
                && estate.getPrice() != null
                && estate.getDevise() != null) {
            id = this.formViewModel.saveEstate(estate);
            if(id > 0) {
                this.formMainLayout.setVisibility(View.GONE);
            }
            return id;
        } else {
            return 0L;
        }
    }
}

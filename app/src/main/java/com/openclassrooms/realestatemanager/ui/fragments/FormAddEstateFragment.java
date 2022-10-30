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
    protected void save() {

        Estate estate = new Estate();
        this.setMandatoryProperties(estate);

        if(estate.getType() != null
                && estate.getLocation() != null
                && estate.getPrice() != null
                && estate.getDevise() != null) {
            Long id = this.formViewModel.saveEstate(estate);
            if(id > 0) {
                this.formMainLayout.setVisibility(View.GONE);
                this.enableAddressFieldsComponants();
            }
        }
    }
}

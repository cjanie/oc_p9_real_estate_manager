package com.openclassrooms.realestatemanager.ui.fragments;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;

public class FormAddEstateFragment extends FormFragment {

    @Override
    public Estate getEstate() {
        return new Estate();
    }

    @Override
    public void save(Estate estate) {
        this.saveEstateViaViewModel(estate);
    }

}

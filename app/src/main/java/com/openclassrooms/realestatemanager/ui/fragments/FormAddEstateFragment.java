package com.openclassrooms.realestatemanager.ui.fragments;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;

public class FormAddEstateFragment extends FormFragment {
    
    @Override
    public Estate getInitializedEstate() {
        return new Estate();
    }

}

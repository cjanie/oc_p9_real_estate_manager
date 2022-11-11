package com.openclassrooms.realestatemanager.ui.fragments;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;

public interface HandleDevise {

    Float getPriceInCurrentDevise(Estate estate);

    String getDeviseAsString();

    Devise getDevise();

}

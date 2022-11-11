package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.DeviseVisitor;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

public class HasDeviseReference {

    protected final Devise reference = Devise.DOLLAR;

    protected Estate checkDeviseReference(Estate estate) {
        if(estate.getDevise().equals(Devise.EURO) && this.reference.equals(Devise.DOLLAR)) {
            estate.setDevise(this.reference);
            Integer convertion = Utils.convertEuroToDollar(Math.round(estate.getPrice()));
            estate.setPrice(convertion.floatValue());
        }
        return estate;
    }
}

package com.openclassrooms.realestatemanager.ui.fragments.form;

public interface FormStepVisitor<T> {
    T visitMandatory();
    T visitMedia();
    T visitGeolocation();
    T visitDescription();
    T visitDescriptionDetails();
    T visitAddress();
    T visitGeocoding();
}

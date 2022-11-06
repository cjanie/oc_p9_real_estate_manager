package com.openclassrooms.realestatemanager.ui.fragments;

public interface FormStepVisitor<T> {
    T visitMandatory();
    T visitMedia();
    T visitGeolocation();
    T visitDescription();
    T visitDescriptionDetails();
    T visitAddress();
}

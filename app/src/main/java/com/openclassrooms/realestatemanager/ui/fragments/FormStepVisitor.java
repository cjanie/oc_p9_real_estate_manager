package com.openclassrooms.realestatemanager.ui.fragments;

public interface FormStepVisitor<T> {
    T visitMandatory();
    T visitMedia();
    T visitDescription();
    T visitDescriptionDetails();
    T visitAddress();
}

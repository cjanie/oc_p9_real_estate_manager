package com.openclassrooms.realestatemanager.ui.fragments.form;

public interface FormFieldVisitor<T> {

    T visitDescription();
    T visitSurface();
    T visitRooms();
    T visitBathrooms();
    T visitBedrooms();
}

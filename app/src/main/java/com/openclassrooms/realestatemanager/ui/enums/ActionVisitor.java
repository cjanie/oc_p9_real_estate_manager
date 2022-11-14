package com.openclassrooms.realestatemanager.ui.enums;

public interface ActionVisitor<T> {

    T visitHome();
    T visitAdd();
    T visitEdit();
    T visitSearch();
    T visitDetails();
    T visitMap();

}

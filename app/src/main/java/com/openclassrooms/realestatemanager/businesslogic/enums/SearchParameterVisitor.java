package com.openclassrooms.realestatemanager.businesslogic.enums;

public interface SearchParameterVisitor<T> {
    T visitType();
    T visitLocation();
    T visitMaxPriceInDollars();
}

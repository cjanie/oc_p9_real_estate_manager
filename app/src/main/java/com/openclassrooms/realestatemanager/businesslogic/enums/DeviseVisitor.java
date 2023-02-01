package com.openclassrooms.realestatemanager.businesslogic.enums;

public interface DeviseVisitor<T> {

    T visitDollar();
    T visitEuro();
}

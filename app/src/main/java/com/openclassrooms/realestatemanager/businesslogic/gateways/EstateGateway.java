package com.openclassrooms.realestatemanager.businesslogic.gateways;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;

import java.util.List;

public interface EstateGateway {

    List<Estate> getEstates();
}

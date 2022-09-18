package com.openclassrooms.realestatemanager.businesslogic.gateways;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;

import java.util.List;

public interface EstateGateway {

    List<Estate> getEstates();

    List<Estate> searchByType(EstateType type);

    List<Estate> searchByLocation(String location);


}

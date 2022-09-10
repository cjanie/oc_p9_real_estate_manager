package com.openclassrooms.realestatemanager.businesslogic.gateways;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;

public interface EstateCommandGateway {

    Long save(Estate estate);

}

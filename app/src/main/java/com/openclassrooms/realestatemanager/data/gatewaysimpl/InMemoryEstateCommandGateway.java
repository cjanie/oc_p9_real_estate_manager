package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateCommandGateway;

public class InMemoryEstateCommandGateway implements EstateCommandGateway {

    @Override
    public Long save(Estate estate) {
        return 1L;
    }
}

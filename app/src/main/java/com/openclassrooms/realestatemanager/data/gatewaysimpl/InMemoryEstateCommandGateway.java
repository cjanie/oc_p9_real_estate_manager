package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateCommandGateway;

public class InMemoryEstateCommandGateway implements EstateCommandGateway {

    private Estate estate;

    public InMemoryEstateCommandGateway() {
    }

    @Override
    public Long save(Estate estate) {
        this.estate = estate;
        return 1L;
    }

    public Estate getEstate() {
        return this.estate;
    }
}

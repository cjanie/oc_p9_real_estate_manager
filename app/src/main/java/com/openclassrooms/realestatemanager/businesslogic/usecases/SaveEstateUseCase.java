package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateCommandGateway;

public class SaveEstateUseCase {

    private EstateCommandGateway estateGateway;

    public SaveEstateUseCase(EstateCommandGateway estateGateway) {
        this.estateGateway = estateGateway;
    }

    public Long handle(Estate estate) {
        return estateGateway.save(estate);
    }
}

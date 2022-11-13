package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateStatus;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateCommandGateway;

public class SaveEstateUseCase extends HasDeviseReference {

    private EstateCommandGateway estateGateway;

    public SaveEstateUseCase(EstateCommandGateway estateGateway) {
        this.estateGateway = estateGateway;
    }

    public Long handle(Estate estate) {
        estate.setStatus(EstateStatus.SALE);
        return estateGateway.save(this.checkDeviseReference(estate));
    }
}

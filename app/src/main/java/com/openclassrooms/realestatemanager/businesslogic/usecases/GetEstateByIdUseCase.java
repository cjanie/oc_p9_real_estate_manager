package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;

public class GetEstateByIdUseCase {

    private EstateGateway estateGateway;

    public GetEstateByIdUseCase(EstateGateway estateGateway) {
        this.estateGateway = estateGateway;
    }

    public Estate handle(int id) {
        return this.estateGateway.getEstateById(id);
    }
}


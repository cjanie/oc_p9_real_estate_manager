package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;
import com.openclassrooms.realestatemanager.businesslogic.gateways.MediaGateway;

import java.util.List;

public class GetEstateByIdUseCase {

    private EstateGateway estateGateway;

    public GetEstateByIdUseCase(EstateGateway estateGateway) {
        this.estateGateway = estateGateway;
    }

    public Estate handle(int id) {
        Estate estate = this.estateGateway.getEstateById(id);

        return estate;
    }
}


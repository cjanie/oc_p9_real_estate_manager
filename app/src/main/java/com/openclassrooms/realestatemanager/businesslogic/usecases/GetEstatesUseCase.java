package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;

import java.util.List;

public class GetEstatesUseCase {

    private EstateGateway estateGateway;

    public GetEstatesUseCase(EstateGateway estateGateway) {
        this.estateGateway = estateGateway;
    }

    public List<Estate> handle() {
        return this.estateGateway.getEstates();
    }

}

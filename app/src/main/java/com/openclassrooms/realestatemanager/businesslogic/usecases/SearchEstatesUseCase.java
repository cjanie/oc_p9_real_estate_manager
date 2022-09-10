package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;

import java.util.List;

public class SearchEstatesUseCase {

    private EstateGateway estateGateway;

    public SearchEstatesUseCase(EstateGateway estateGateway) {
        this.estateGateway = estateGateway;
    }

    public List<Estate> handle(EstateType type) {
        return this.estateGateway.search(type);
    }

}

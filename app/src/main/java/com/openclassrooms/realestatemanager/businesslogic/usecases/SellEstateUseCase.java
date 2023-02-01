package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Agent;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateStatus;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateCommandGateway;

import java.time.LocalDate;

public class SellEstateUseCase {

    private EstateCommandGateway estateCommandGateway;

    public SellEstateUseCase(EstateCommandGateway estateCommandGateway) {
        this.estateCommandGateway = estateCommandGateway;
    }

    public Long handle(Estate estate, String agentName) {

        estate.setDateOfSale(LocalDate.now());
        Agent agent = new Agent();
        agent.setName(agentName);
        estate.setAgent(agent);
        estate.setStatus(EstateStatus.SOLD);
        return this.estateCommandGateway.save(estate);
    }
}

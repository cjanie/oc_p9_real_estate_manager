package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Agent;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateStatus;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateCommandGateway;
import com.openclassrooms.realestatemanager.dateprovider.DateProvider;

import java.time.LocalDate;

public class SaveEstateUseCase extends HasDeviseReference {

    private EstateCommandGateway estateGateway;

    private DateProvider dateProvider;

    public SaveEstateUseCase(EstateCommandGateway estateGateway, DateProvider dateProvider) {
        this.estateGateway = estateGateway;
        this.dateProvider = dateProvider;
    }

    public Long handle(Estate estate, String agentName) {
        if (estate.getAgent() == null) {
            Agent agent = new Agent();
            agent.setName(agentName);
            estate.setAgent(agent);
        }
        if(estate.getStatus() == null) {
            estate.setStatus(EstateStatus.SALE);
        }
        if(estate.getDateOfEntreeIntoMarket() == null) {
            LocalDate startDate = this.dateProvider.today();
            estate.setDateOfEntreeIntoMarket(startDate);
        }
        return this.save(estate);
    }

    public Long handleUpdate(Estate estate) {
        return this.save(estate);
    }

    private Long save(Estate estate) {
        return estateGateway.save(this.checkDeviseReference(estate));
    }
}

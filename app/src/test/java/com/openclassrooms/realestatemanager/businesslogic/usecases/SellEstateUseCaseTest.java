package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateStatus;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryEstateCommandGateway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SellEstateUseCaseTest {

    @Test
    public void estateMustHaveADateOfSale() {
        InMemoryEstateCommandGateway commandGateway = new InMemoryEstateCommandGateway();
        SellEstateUseCase sellUseCase = new SellEstateUseCase(commandGateway);
        sellUseCase.handle(new Estate(), "");
        Assertions.assertNotNull(commandGateway.getEstate().getDateOfSale());
    }

    @Test
    public void mustMentionSeller() {
        InMemoryEstateCommandGateway commandGateway = new InMemoryEstateCommandGateway();
        SellEstateUseCase sellUseCase = new SellEstateUseCase(commandGateway);
        sellUseCase.handle(new Estate(), "Janie");
        Assertions.assertEquals("Janie", commandGateway.getEstate().getAgent().getName());
    }

    @Test
    public void mustChangeStatusToSold() {
        InMemoryEstateCommandGateway commandGateway = new InMemoryEstateCommandGateway();
        SellEstateUseCase sellUseCase = new SellEstateUseCase(commandGateway);
        sellUseCase.handle(new Estate(), "Janie");
        Assertions.assertEquals(EstateStatus.SOLD, commandGateway.getEstate().getStatus());
    }
}

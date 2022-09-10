package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryEstateCommandGateway;

import org.junit.Assert;
import org.junit.Test;





public class SaveEstateUseCaseTest {

    @Test
    public void saveReturnsId() {
        InMemoryEstateCommandGateway estateGateway = new InMemoryEstateCommandGateway();
        long addedId = new SaveEstateUseCase(estateGateway).handle(new Estate());
        Assert.assertEquals(1, addedId, 0);
    }
}

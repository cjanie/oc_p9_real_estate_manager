package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryEstateCommandGateway;

import org.junit.Assert;
import org.junit.Test;





public class SaveEstateUseCaseTest {

    @Test
    public void saveReturnsId() {
        InMemoryEstateCommandGateway estateGateway = new InMemoryEstateCommandGateway();
        Estate estate = new Estate();
        estate.setDevise(Devise.DOLLAR);
        long addedId = new SaveEstateUseCase(estateGateway).handle(estate);
        Assert.assertEquals(1, addedId, 0);
    }

    @Test
    public void savesOnlyInDollar() {
        InMemoryEstateCommandGateway commandGateway = new InMemoryEstateCommandGateway();
        Estate estate = new Estate();
        estate.setPrice(35f);
        estate.setDevise(Devise.EURO);
        new SaveEstateUseCase(commandGateway).handle(estate);
        Assert.assertEquals(Devise.DOLLAR, commandGateway.getEstate().getDevise());
        Assert.assertNotEquals(35f, commandGateway.getEstate().getPrice());
    }
}

package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryEstateGateway;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class GetEstateByIdUseCaseTest {

    @Test
    public void returnsEstateWhenFound() {
        // Prepare
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate = new Estate();
        estate.setId(1);
        estateGateway.setEstates(Arrays.asList(estate));

        // Instanciate SUT
        GetEstateByIdUseCase getEstateByIdUseCase = new GetEstateByIdUseCase(estateGateway);

        // Assert
        Assert.assertNotNull(getEstateByIdUseCase.handle(1));
    }

    @Test
    public void returnNothingWhenNotFound() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();

        // Instanciate SUT
        GetEstateByIdUseCase getEstateByIdUseCase = new GetEstateByIdUseCase(estateGateway);

        // Assert
        Assert.assertNull(getEstateByIdUseCase.handle(1));
    }

}

package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryEstateGateway;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class GetEstateByIdUseCaseTest {

    @Test
    public void returnsEstateWhenFound() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate = new Estate();
        estate.setId(1);
        estateGateway.setEstates(Arrays.asList(estate));
        GetEstateByIdUseCase getEstateByIdUseCase = new GetEstateByIdUseCase(estateGateway);
        Assert.assertNotNull(getEstateByIdUseCase.handle(1));
    }

    @Test
    public void returnNothingWhenNotFound() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        GetEstateByIdUseCase getEstateByIdUseCase = new GetEstateByIdUseCase(estateGateway);
        Assert.assertNull(getEstateByIdUseCase.handle(1));
    }
}

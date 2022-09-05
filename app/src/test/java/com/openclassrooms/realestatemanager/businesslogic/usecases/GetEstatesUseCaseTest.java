package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryEstateGateway;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class GetEstatesUseCaseTest {

    @Test
    public void returnsEstatesWhenThereAreSome() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        estateGateway.setEstates(Arrays.asList(new Estate(), new Estate()));
        List<Estate> results = new GetEstatesUseCase(estateGateway).handle();
        Assert.assertEquals(2, results.size());
    }

    @Test
    public void doestNotReturnAnyEstateWhenThereIsNone() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        List<Estate> results = new GetEstatesUseCase(estateGateway).handle();
        Assert.assertEquals(0, results.size());
    }
}

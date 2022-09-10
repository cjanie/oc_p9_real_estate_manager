package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryEstateGateway;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchEstatesUseCaseTest {

    @Test
    public void returnsNothingWhenNothingIsFound() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        List<Estate> found = new SearchEstatesUseCase(estateGateway).handle(EstateType.FLAT);
        Assert.assertEquals(0, found.size(), 0);
    }

    @Test
    public void returnsResultsWhenFound() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        estateGateway.setEstates(Arrays.asList(new Estate(), new Estate()));
        List<Estate> found = new SearchEstatesUseCase(estateGateway).handle(EstateType.FLAT);
        Assert.assertEquals(2, found.size(), 0);
    }
}

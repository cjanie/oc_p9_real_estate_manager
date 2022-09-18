package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.MapSearchEstatesParamsConfig;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryEstateGateway;


import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchEstatesUseCaseTest {

    @Test
    public void returnsNothingWhenNothingIsFound() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        List<Estate> found = new SearchEstatesUseCase(estateGateway).find(EstateType.FLAT);
        Assert.assertEquals(0, found.size(), 0);
    }

    @Test
    public void returnsResultsWhenFound() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate1 = new Estate();
        estate1.setType(EstateType.FLAT);
        Estate estate2 = new Estate();
        estate2.setType(EstateType.FLAT);
        estateGateway.setEstates(Arrays.asList(estate1, estate2));
        List<Estate> found = new SearchEstatesUseCase(estateGateway).find(EstateType.FLAT);
        Assert.assertEquals(2, found.size(), 0);
    }

    @Test
    public void searchByLocationReturnsResultWhenEstateMatchesParameter() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate1 = new Estate();
        estate1.setType(EstateType.FLAT);
        estate1.setLocation("Paris");
        estateGateway.setEstates(Arrays.asList(estate1));
        List<Estate> found = new SearchEstatesUseCase(estateGateway).find( "Paris");
        Assert.assertEquals(1, found.size());
    }

    @Test
    public void searchByMultipleParametersReturnsResultsWhenAllParametersMatch() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate1 = new Estate();
        estate1.setType(EstateType.FLAT);
        estate1.setLocation("Paris");
        estateGateway.setEstates(Arrays.asList(estate1));
        Map<String, Object> searchParamsMap = new HashMap<>();
        searchParamsMap.put(MapSearchEstatesParamsConfig.TYPE, EstateType.FLAT);
        searchParamsMap.put(MapSearchEstatesParamsConfig.LOCATION, "Paris");
        List<Estate> found = new SearchEstatesUseCase(estateGateway).find(searchParamsMap);
        Assert.assertEquals(1, found.size());
    }

    @Test
    public void searchByMultipleParametersReturnsResultsOnlyWhenAllParametersMatch() {

        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate1 = new Estate();
        estate1.setType(EstateType.DUPLEX);
        Estate estate2 = new Estate();
        estate2.setLocation("Paris");
        estateGateway.setEstates(Arrays.asList(estate1, estate2));
        Map<String, Object> searchParamsMap = new HashMap<>();
        searchParamsMap.put(MapSearchEstatesParamsConfig.TYPE, EstateType.DUPLEX);
        searchParamsMap.put(MapSearchEstatesParamsConfig.LOCATION, "Paris");
        List<Estate> found = new SearchEstatesUseCase(estateGateway)
                .find(searchParamsMap);
        Assert.assertEquals(0, found.size());
    }

    @Test
    public void searchByOnlyOneParameterLikeLocation() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate1 = new Estate();
        estate1.setLocation("Paris");
        estateGateway.setEstates(Arrays.asList(estate1));

        Map<String, Object> searchParamsMap = new HashMap<>();
        searchParamsMap.put(MapSearchEstatesParamsConfig.LOCATION, "Paris");
        List<Estate> found = new SearchEstatesUseCase(estateGateway)
                .find(searchParamsMap);
        Assert.assertEquals(1, found.size());
    }

    @Test
    public void searchByOnlyOneParameterLikeType() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate1 = new Estate();
        estate1.setType(EstateType.FLAT);
        estate1.setLocation("Paris");
        Estate estate2 = new Estate();
        estate2.setType(EstateType.DUPLEX);
        estateGateway.setEstates(Arrays.asList(estate1, estate2));
        Map<String, Object> searchParamsMap = new HashMap<>();
        searchParamsMap.put(MapSearchEstatesParamsConfig.TYPE, EstateType.DUPLEX);
        List<Estate> found = new SearchEstatesUseCase(estateGateway)
                .find(searchParamsMap);
        Assert.assertEquals(1, found.size());
    }
}

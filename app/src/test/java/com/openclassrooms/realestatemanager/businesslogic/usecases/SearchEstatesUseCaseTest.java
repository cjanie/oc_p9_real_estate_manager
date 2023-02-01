package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.businesslogic.enums.SearchParameter;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryEstateGateway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchEstatesUseCaseTest {

    @Test
    public void returnsNothingWhenNothingIsFound() {

        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();

        // Config parameters for search
        Map<SearchParameter, Object> searchParams = new HashMap<>();
        searchParams.put(SearchParameter.TYPE, EstateType.FLAT);

        // SUT
        List<Estate> found = new SearchEstatesUseCase(estateGateway).handle(searchParams, Devise.DOLLAR);

        // Assertion
        Assertions.assertEquals(0, found.size(), 0);
    }

    @Test
    public void returnsResultsWhenFound() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate1 = new Estate();
        estate1.setType(EstateType.FLAT);
        Estate estate2 = new Estate();
        estate2.setType(EstateType.FLAT);
        estateGateway.setEstates(Arrays.asList(estate1, estate2));

        Map<SearchParameter, Object> params = new HashMap<>();
        params.put(SearchParameter.TYPE, EstateType.FLAT);
        List<Estate> found = new SearchEstatesUseCase(estateGateway).handle(params, Devise.DOLLAR);
        Assertions.assertEquals(2, found.size(), 0);
    }

    @Test
    public void searchByLocationReturnsResultWhenEstateMatchesParameter() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate1 = new Estate();
        estate1.setType(EstateType.FLAT);
        estate1.setLocation("Paris");
        estateGateway.setEstates(Arrays.asList(estate1));

        Map<SearchParameter, Object> params = new HashMap<>();
        params.put(SearchParameter.LOCATION, "Paris");
        List<Estate> found = new SearchEstatesUseCase(estateGateway).handle(params, Devise.DOLLAR);
        Assertions.assertEquals(1, found.size());
    }

    @Test
    public void searchByMultipleParametersReturnsResultsWhenAllParametersMatch() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate1 = new Estate();
        estate1.setType(EstateType.FLAT);
        estate1.setLocation("Paris");
        Estate estate2 = new Estate();
        estate2.setType(EstateType.FLAT);
        estate2.setLocation("Paris");
        estateGateway.setEstates(Arrays.asList(estate1, estate2));

        Map<SearchParameter, Object> searchParamsMap = new HashMap<>();
        searchParamsMap.put(SearchParameter.TYPE, EstateType.FLAT);
        searchParamsMap.put(SearchParameter.LOCATION, "Paris");

        List<Estate> found = new SearchEstatesUseCase(estateGateway).handle(searchParamsMap, Devise.DOLLAR);

        Assertions.assertEquals(2, found.size());
    }

    @Test
    public void searchByMultipleParametersReturnsResultsOnlyWhenAllParametersMatch() {

        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();

        Estate estate1 = new Estate();
        estate1.setType(EstateType.DUPLEX);

        Estate estate2 = new Estate();
        estate2.setLocation("Paris");

        Estate estate3 = new Estate();
        estate3.setType(EstateType.FLAT);
        estate3.setLocation("Paris");

        Estate estate4 = new Estate();
        estate4.setType(EstateType.DUPLEX);
        estate4.setLocation("Montpellier");

        Estate estate5 = new Estate();
        estate5.setType(EstateType.DUPLEX);
        estate5.setLocation("Paris");

        estateGateway.setEstates(Arrays.asList(estate1, estate2, estate3, estate4, estate5));
        Map<SearchParameter, Object> searchParamsMap = new HashMap<>();
        searchParamsMap.put(SearchParameter.TYPE, EstateType.DUPLEX);
        searchParamsMap.put(SearchParameter.LOCATION, "Paris");

        SearchEstatesUseCase searchUseCase = new SearchEstatesUseCase(estateGateway);

        List<Estate> found = searchUseCase.handle(searchParamsMap, Devise.DOLLAR);
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals(EstateType.DUPLEX, found.get(0).getType());
        Assertions.assertEquals("Paris", found.get(0).getLocation());
    }

    @Test
    public void searchByOnlyOneParameterLikeLocation() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate1 = new Estate();
        estate1.setLocation("Paris");
        estateGateway.setEstates(Arrays.asList(estate1));

        Map<SearchParameter, Object> searchParamsMap = new HashMap<>();
        searchParamsMap.put(SearchParameter.LOCATION, "Paris");
        List<Estate> found = new SearchEstatesUseCase(estateGateway)
                .handle(searchParamsMap, Devise.DOLLAR);
        Assertions.assertEquals(1, found.size());
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

        Map<SearchParameter, Object> searchParamsMap = new HashMap<>();
        searchParamsMap.put(SearchParameter.TYPE, EstateType.DUPLEX);
        List<Estate> found = new SearchEstatesUseCase(estateGateway)
                .handle(searchParamsMap, Devise.DOLLAR);

        Assertions.assertEquals(1, found.size());
    }

    @Test
    public void searchByOnlyOneParameterLikeMaxPriceReturnsNothingWhenNotFound() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate1 = new Estate();
        estate1.setType(EstateType.DUPLEX);
        estate1.setPrice(100000f);
        estateGateway.setEstates(Arrays.asList(estate1));

        Map<SearchParameter, Object> searchParamsMap = new HashMap<>();
        searchParamsMap.put(SearchParameter.MAX_PRICE_IN_DOLLARS, 5000f);
        List<Estate> found = new SearchEstatesUseCase(estateGateway)
                .handle(searchParamsMap, Devise.DOLLAR);
        Assertions.assertEquals(0, found.size());
    }


    @Test
    public void searchByOnlyOneParameterLikeUnderMaxPriceReturnsFound() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate1 = new Estate();
        estate1.setType(EstateType.DUPLEX);
        estate1.setLocation("Paris");
        estate1.setPrice(100000f);
        estateGateway.setEstates(Arrays.asList(estate1));

        Map<SearchParameter, Object> searchParamsMap = new HashMap<>();
        searchParamsMap.put(SearchParameter.MAX_PRICE_IN_DOLLARS, 2000000f);

        List<Estate> found = new SearchEstatesUseCase(estateGateway)
                .handle(searchParamsMap, Devise.DOLLAR);
        Assertions.assertEquals(1, found.size());
    }

    @Test
    public void searchByAllParametersReturnsResultsWhenFoundAllParametersMatching() {
        // Prepare
        // Data in gateway
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();

        Estate estate1 = new Estate();
        estate1.setType(EstateType.DUPLEX);
        estate1.setLocation("Paris");
        estate1.setPrice(100000f);

        Estate estate2 = new Estate();
        estate2.setType(EstateType.DUPLEX);
        estate2.setLocation("Paris");
        estate2.setPrice(300000f);

        estateGateway.setEstates(Arrays.asList(estate1, estate2));

        // Search parameters
        Map<SearchParameter, Object> params = new HashMap<>();
        params.put(SearchParameter.TYPE, EstateType.DUPLEX);
        params.put(SearchParameter.LOCATION, "Paris");
        params.put(SearchParameter.MAX_PRICE_IN_DOLLARS, 200000f);

        // SUT
        SearchEstatesUseCase searchUseCase = new SearchEstatesUseCase(estateGateway);
        List<Estate> results = searchUseCase.handle(params, Devise.DOLLAR);

        // Assertion
        Assertions.assertEquals(1, results.size());
    }

    @Test
    public void whenNotAllParametersAreSelected() {
        // Prepare
        // Data in gateway
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();

        Estate estate1 = new Estate();
        estate1.setPrice(100000f);

        Estate estate2 = new Estate();
        estate2.setLocation("Paris");
        estate2.setPrice(300000f);

        estateGateway.setEstates(Arrays.asList(estate1, estate2));

        // Search parameters
        Map<SearchParameter, Object> params = new HashMap<>();
        params.put(SearchParameter.LOCATION, "Paris");
        params.put(SearchParameter.MAX_PRICE_IN_DOLLARS, 200000f);

        // SUT
        SearchEstatesUseCase searchUseCase = new SearchEstatesUseCase(estateGateway);
        List<Estate> results = searchUseCase.handle(params, Devise.DOLLAR);

        // Assertion
        Assertions.assertEquals(0, results.size());
    }
}

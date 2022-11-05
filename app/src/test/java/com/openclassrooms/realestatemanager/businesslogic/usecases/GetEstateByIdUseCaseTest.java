package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryEstateGateway;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryMediaGateway;

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

        InMemoryMediaGateway mediaGateway = new InMemoryMediaGateway();

        // Instanciate SUT
        GetEstateByIdUseCase getEstateByIdUseCase = new GetEstateByIdUseCase(estateGateway, mediaGateway);

        // Assert
        Assert.assertNotNull(getEstateByIdUseCase.handle(1));
    }

    @Test
    public void returnNothingWhenNotFound() {
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        InMemoryMediaGateway mediaGateway = new InMemoryMediaGateway();

        // Instanciate SUT
        GetEstateByIdUseCase getEstateByIdUseCase = new GetEstateByIdUseCase(estateGateway, mediaGateway);

        // Assert
        Assert.assertNull(getEstateByIdUseCase.handle(1));
    }

    @Test
    public void estateHasMediaWhenFound() {
        // Prepare
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate = new Estate();
        estate.setId(1);
        estateGateway.setEstates(Arrays.asList(estate));

        InMemoryMediaGateway mediaGateway = new InMemoryMediaGateway();
        mediaGateway.setMedia(Arrays.asList("img-1"));
        // Instanciate SUT
        GetEstateByIdUseCase getEstateByIdUseCase = new GetEstateByIdUseCase(estateGateway, mediaGateway);
        // Assert
        Assert.assertEquals(1, getEstateByIdUseCase.handle(1).getMedia().size());
    }

    @Test
    public void estateHasNoMediaWhenNotFound() {
        // Prepare
        InMemoryEstateGateway estateGateway = new InMemoryEstateGateway();
        Estate estate = new Estate();
        estate.setId(1);
        estateGateway.setEstates(Arrays.asList(estate));

        InMemoryMediaGateway mediaGateway = new InMemoryMediaGateway();

        // Instanciate SUT
        GetEstateByIdUseCase getEstateByIdUseCase = new GetEstateByIdUseCase(estateGateway, mediaGateway);

        // Assert
        Assert.assertEquals(0, getEstateByIdUseCase.handle(1).getMedia().size());
    };
}

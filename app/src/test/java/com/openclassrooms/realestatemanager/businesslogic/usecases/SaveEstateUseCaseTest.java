package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateStatus;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryEstateCommandGateway;
import com.openclassrooms.realestatemanager.dateprovider.DeterministicDateProvider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


public class SaveEstateUseCaseTest {

    @Test
    public void saveReturnsId() {
        InMemoryEstateCommandGateway estateGateway = new InMemoryEstateCommandGateway();
        Estate estate = new Estate();
        estate.setDevise(Devise.DOLLAR);

        DeterministicDateProvider dateProvider
                = new DeterministicDateProvider(LocalDate.of(
                2022, 12, 5)
        );

        long addedId = new SaveEstateUseCase(estateGateway, dateProvider).handle(estate, "Janie");
        Assertions.assertEquals(1, addedId, 0);
    }

    @Test
    public void savesOnlyInDollar() {
        InMemoryEstateCommandGateway commandGateway = new InMemoryEstateCommandGateway();
        Estate estate = new Estate();
        estate.setPrice(35f);
        estate.setDevise(Devise.EURO);

        DeterministicDateProvider dateProvider
                = new DeterministicDateProvider(LocalDate.of(
                2022, 12, 5)
        );

        new SaveEstateUseCase(commandGateway, dateProvider).handle(estate, "Janie");
        Assertions.assertEquals(Devise.DOLLAR, commandGateway.getEstate().getDevise());
        Assertions.assertNotEquals(35f, commandGateway.getEstate().getPrice());
    }

    @Test
    public void savesEstateStatusSaleAsDefault() {
        InMemoryEstateCommandGateway commandGateway = new InMemoryEstateCommandGateway();
        Estate estate = new Estate();
        estate.setPrice(35f);
        estate.setDevise(Devise.EURO);

        DeterministicDateProvider dateProvider
                = new DeterministicDateProvider(LocalDate.of(
                        2022, 12, 5)
        );

        new SaveEstateUseCase(commandGateway, dateProvider).handle(estate, "Janie");
        Assertions.assertEquals(EstateStatus.SALE, commandGateway.getEstate().getStatus());
    }

    @Test
    public void savesOnlyWithDefinedDateOfCreation() {
        InMemoryEstateCommandGateway commandGateway = new InMemoryEstateCommandGateway();
        Estate estate = new Estate();
        estate.setPrice(35f);
        estate.setDevise(Devise.EURO);

        DeterministicDateProvider dateProvider
                = new DeterministicDateProvider(LocalDate.of(
                2022, 12, 5)
        );

        new SaveEstateUseCase(commandGateway, dateProvider).handle(estate, "Janie");
        Assertions.assertEquals(5, commandGateway.getEstate().getDateOfEntreeIntoMarket().getDayOfMonth());
    }

    @Test
    public void savesOnlyWithDefinedAgent() {
        InMemoryEstateCommandGateway commandGateway = new InMemoryEstateCommandGateway();
        Estate estate = new Estate();
        estate.setPrice(35f);
        estate.setDevise(Devise.EURO);

        DeterministicDateProvider dateProvider
                = new DeterministicDateProvider(LocalDate.of(
                2022, 12, 5)
        );

        new SaveEstateUseCase(commandGateway, dateProvider).handle(estate, "Jogo");
        Assertions.assertEquals("Jogo", commandGateway.getEstate().getAgent().getName());
    }

}

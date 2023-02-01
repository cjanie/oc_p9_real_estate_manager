package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.dateprovider.DeterministicDateProvider;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UtilsTest {

    @Test
    public void convertDollarsToEuros() {
        // 1 $ = 0.812 €
        // 1/0.812 = 1.23 $ = 1 €
        Assertions.assertEquals(8, Utils.convertDollarToEuro(10), 0);
    }

    @Test
    public void convertEurosToDollars() {
        Assertions.assertEquals(12, Utils.convertEuroToDollar(10), 0);
    }

    @Test
    public void formatDate() {
        DeterministicDateProvider dateProvider = new DeterministicDateProvider(LocalDate.of(2022, 1, 15));
        Assertions.assertEquals("15/01/2022", Utils.getToday(dateProvider));
    }
}

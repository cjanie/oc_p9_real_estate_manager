package com.openclassrooms.realestatemanager;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class UtilsTest {

    @Test
    public void convertDollarsToEuros() {
        // 1 $ = 0.812 €
        // 1/0.812 = 1.23 $ = 1 €
        Assert.assertEquals(8, Utils.convertDollarToEuro(10), 0);
    }

    @Test
    public void convertEurosToDollars() {
        Assert.assertEquals(12, Utils.convertEuroToDollar(10), 0);
    }

    @Test
    public void formatDate() {
        DeterministicDateProvider dateProvider = new DeterministicDateProvider(LocalDate.of(2022, 1, 15));
        Assert.assertEquals("15/01/2022", Utils.getToday(dateProvider));
    }
}

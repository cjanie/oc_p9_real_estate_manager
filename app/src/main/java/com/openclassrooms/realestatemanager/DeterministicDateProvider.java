package com.openclassrooms.realestatemanager;

import java.time.LocalDate;
import java.util.Date;

public class DeterministicDateProvider implements DateProvider {

    private LocalDate today;

    public DeterministicDateProvider(LocalDate today) {
        this.today = today;
    }

    @Override
    public LocalDate today() {
        return this.today;
    }
}

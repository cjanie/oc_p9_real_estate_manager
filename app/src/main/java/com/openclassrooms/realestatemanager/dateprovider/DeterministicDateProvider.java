package com.openclassrooms.realestatemanager.dateprovider;

import com.openclassrooms.realestatemanager.dateprovider.DateProvider;

import java.time.LocalDate;

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

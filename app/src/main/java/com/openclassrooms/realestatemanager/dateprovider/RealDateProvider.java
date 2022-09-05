package com.openclassrooms.realestatemanager.dateprovider;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.openclassrooms.realestatemanager.dateprovider.DateProvider;

import java.time.LocalDate;

public class RealDateProvider implements DateProvider {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public LocalDate today() {
        return LocalDate.now();
    }

}

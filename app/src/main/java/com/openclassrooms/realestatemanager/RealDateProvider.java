package com.openclassrooms.realestatemanager;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Date;

public class RealDateProvider implements DateProvider {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public LocalDate today() {
        return LocalDate.now();
    }

}

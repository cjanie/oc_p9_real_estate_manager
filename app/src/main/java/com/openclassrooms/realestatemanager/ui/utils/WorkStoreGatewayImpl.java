package com.openclassrooms.realestatemanager.ui.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class WorkStoreGatewayImpl implements WorkStoreGateway {

    private SharedPreferences sharedPreferences;

    private final String preferencesName = WorkStoreGatewayConfig.PREFERENCES_NAME;
    private final String preferenceKey = WorkStoreGatewayConfig.PREFERENCE_KEY;


    public WorkStoreGatewayImpl(Application application) {
        this.sharedPreferences = application.getSharedPreferences(this.preferencesName, Context.MODE_PRIVATE);
    }

    @Override
    public Set<String> getBacklog() {
        return this.sharedPreferences.getStringSet(this.preferenceKey, new HashSet<>());
    }
}

package com.openclassrooms.realestatemanager.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.usecases.GetEstateByIdUseCase;
import com.openclassrooms.realestatemanager.businesslogic.usecases.SaveEstateUseCase;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases.GeolocalizeFromAddressUseCase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GeocodingRequestWorker extends Worker {

    GeolocalizeFromAddressUseCase geolocalizeFromAddressUseCase;

    GetEstateByIdUseCase getEstateByIdUseCase;

    SaveEstateUseCase saveEstateUseCase;

    WorkStore workStore;

    List<Estate> estatesPayloads;

    public GeocodingRequestWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.geolocalizeFromAddressUseCase = ((Launch) context.getApplicationContext()).geolocalizeFromAddressUseCase();
        this.getEstateByIdUseCase = ((Launch) context.getApplicationContext()).getEstateByIdUseCase();
        this.saveEstateUseCase = ((Launch) context.getApplicationContext()).saveEstateUseCase();
        this.workStore = ((Launch) context.getApplicationContext()).workStore();

        this.estatesPayloads = new ArrayList<>();

        this.updateEstatesPayloads();
    }

    private void updateEstatesPayloads() {
        if(!this.workStore.getBackLog().isEmpty()) {
            for (String id : this.workStore.getBackLog()) {
                this.estatesPayloads.add(this.getEstateByIdUseCase.handle(Integer.parseInt(id)));
            }
        }
        this.workStore.clearBacklog();
    }


    @NonNull
    @Override
    public Result doWork() {
        Log.i(this.getClass().getName(), "doWork() backlog size = " + this.workStore.getBackLog().size());
        this.requestGeocoding();
        Log.i(this.getClass().getName(), "doWork()");
        Log.i(this.getClass().getName(), "doWork() backlog new size = " + this.workStore.getBackLog().size());
        return Result.success();
    }

    private void requestGeocoding() {
        Log.i(this.getClass().getName(), "requestGeocoding() estatesPayloads size = " + this.estatesPayloads.size());
        for (int i=0; i<this.estatesPayloads.size(); i++) {
            int index = i;
            this.geolocalizeFromAddressUseCase.getGeolocationResults(estatesPayloads.get(index)).subscribe(
                    results -> {
                        if(!results.isEmpty()) {
                            Log.i(this.getClass().getName(), "requestGeocoding() results latitude = " + results.get(0).getLatitude());
                            estatesPayloads.get(index).setLatitude(results.get(0).getLatitude());
                            estatesPayloads.get(index).setLongitude(results.get(0).getLongitude());
                            // SAVE update
                            Long saved = this.saveEstateUseCase.handleUpdate(estatesPayloads.get(index));
                            Log.i(this.getClass().getName(), "requestGeocoding() saved estate id = " + saved + ", latitude = " + estatesPayloads.get(index).getLatitude());
                        }
                    }
            );
        }
    }
}

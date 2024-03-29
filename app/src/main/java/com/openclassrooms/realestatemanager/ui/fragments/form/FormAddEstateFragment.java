package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateStatus;
import com.openclassrooms.realestatemanager.ui.LocationActivity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class FormAddEstateFragment extends FormFragment {

    private HandleNotification handleNotification;

    public FormAddEstateFragment(LocationActivity locationActivity, HandleNotification handleNotification) {
        super(locationActivity);
        this.handleNotification = handleNotification;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        this.formViewModel.setEstateData(new Estate());
        return root;
    }

    @Override
    public Estate getInitializedEstate() {
        Estate estate = new Estate();
        return estate;
    }

    @Override
    public void saveEstateDataUpdate() {
        Long id = this.formViewModel.saveEstateDataUpdate();

        if(id != null && id > 0) {
            // For notification
            this.handleNotification.setSavedEstateToNotify(this.getData());
            // For geocoding request worker
            if(this.isCompleteAddress(this.getData())) {
                this.saveIdForGeocodingRequestWorker(id);
            }
        }
    }

    public interface HandleNotification {
        void setSavedEstateToNotify(Estate estate);
    }

}

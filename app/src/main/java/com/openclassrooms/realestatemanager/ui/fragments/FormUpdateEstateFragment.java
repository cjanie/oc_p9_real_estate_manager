package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.ui.fragments.FormFragment;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;

public class FormUpdateEstateFragment extends FormFragment {

    private SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.sharedViewModel = new ViewModelProvider(this.requireActivity()).get(SharedViewModel.class);
        this.sharedViewModel.getEstateSelection().observe(this.getViewLifecycleOwner(), estateSelection -> {
            this.id = estateSelection.getId();
            this.type.setText(estateSelection.getType().toString());
            this.location.setText(estateSelection.getLocation());
            this.price.setText(estateSelection.getPrice().toString());
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected Long save() {
        Estate estate = new Estate();

        // Id
        estate.setId(this.id);

        if(!TextUtils.isEmpty(this.type.getText())
                && !TextUtils.isEmpty(this.location.getText())
                && !TextUtils.isEmpty(this.price.getText())) {

            // Type
            estate.setType(this.getEstateType() != null ? this.getEstateType() : this.sharedViewModel.getEstateSelection().getValue().getType());




            // Location
            estate.setLocation(this.location.getText().toString());

            // Price
            try {
                estate.setPrice(Float.parseFloat(this.price.getText().toString()));
                estate.setDevise(Devise.DOLLAR);
                estate.setSurface(1000);
                estate.setNumberOfRooms(3);
                estate.setNumberOfBathrooms(1);
                estate.setNumberOfBedrooms(2);
                return this.formViewModel.saveEstate(estate);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return 0L;

        } else {
            return 0L;
        }
    }
}

package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.ui.exceptions.IncorrectEstateTypeException;
import com.openclassrooms.realestatemanager.ui.exceptions.MandatoryException;
import com.openclassrooms.realestatemanager.ui.viewmodels.DetailsViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.DetailsViewModelFactory;

public class FormUpdateEstateFragment extends FormFragment {

    private SharedViewModel sharedViewModel;
    private DetailsViewModel detailsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        this.sharedViewModel = new ViewModelProvider(this.requireActivity()).get(SharedViewModel.class);

        DetailsViewModelFactory detailsViewModelFactory = ((Launch)this.getActivity().getApplication()).detailsViewModelFactory();
        this.detailsViewModel = new ViewModelProvider(this.getActivity(), detailsViewModelFactory).get(DetailsViewModel.class);

        this.detailsViewModel.getEstate().observe(this.getActivity(), estate -> {
            this.id = estate.getId();
            this.type.setText(estate.getType().toString());
            this.location.setText(estate.getLocation());
            this.price.setText(estate.getPrice().toString());
        });

        this.sharedViewModel.getEstateSelectionId().observe(this.getViewLifecycleOwner(), estateSelectionId -> {
            this.detailsViewModel.fetchEstateToUpdateLiveData(estateSelectionId);
        });

        return root;
    }

    @Override
    protected void save() {
        Estate estate = new Estate();

        // Id
        estate.setId(this.id);

        try {
            estate.setType(this.getEstateType());
        } catch (MandatoryException e) {
            e.printStackTrace();
        }

        if(!TextUtils.isEmpty(this.type.getText())
                && !TextUtils.isEmpty(this.location.getText())
                && !TextUtils.isEmpty(this.price.getText())) {

            // Location
            estate.setLocation(this.location.getText().toString());
            estate.setStreetNumberAndStreetName(this.streetNumberAndStreetName.toString());
            estate.setAddressComplements(this.addressComplements.toString());
            estate.setZipCode(this.zipCode.toString());
            estate.setCountry(this.country.toString());
            // Price
            try {
                estate.setPrice(Float.parseFloat(this.price.getText().toString()));
                estate.setDevise(Devise.DOLLAR);
                // Extra
                if(!TextUtils.isEmpty(this.surface.getText())) {
                    estate.setSurface(Integer.parseInt(this.surface.getText().toString()));
                }
                if(!TextUtils.isEmpty(this.numberOfRooms.getText())) {
                    estate.setNumberOfRooms(Integer.parseInt(this.numberOfRooms.getText().toString()));
                }
                if(!TextUtils.isEmpty(this.numberOfBathrooms.getText())) {
                    estate.setNumberOfBathrooms(Integer.parseInt(this.numberOfBathrooms.getText().toString()));
                }
                if(!TextUtils.isEmpty(this.numberOfBedrooms.getText())) {
                    estate.setNumberOfBedrooms(Integer.parseInt(this.numberOfBedrooms.getText().toString()));
                }
                this.formViewModel.saveEstate(estate);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}

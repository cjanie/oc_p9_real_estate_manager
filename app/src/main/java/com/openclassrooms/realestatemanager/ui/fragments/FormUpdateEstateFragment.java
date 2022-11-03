package com.openclassrooms.realestatemanager.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.ui.exceptions.IncorrectEstateTypeException;
import com.openclassrooms.realestatemanager.ui.exceptions.MandatoryException;
import com.openclassrooms.realestatemanager.ui.viewmodels.DetailsViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.DetailsViewModelFactory;

public class FormUpdateEstateFragment extends FormFragment {

    private DetailsViewModel detailsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        this.sharedViewModel = new ViewModelProvider(this.requireActivity()).get(SharedViewModel.class);

        DetailsViewModelFactory detailsViewModelFactory = ((Launch)this.getActivity().getApplication()).detailsViewModelFactory();
        this.detailsViewModel = new ViewModelProvider(this.getActivity(), detailsViewModelFactory).get(DetailsViewModel.class);

        this.detailsViewModel.getEstate().observe(this.getActivity(), estate -> {
            if(estate != null) {


                this.formViewModel.setEstateData(estate);

                boolean isCompleteMandatory = estate.getType() != null
                        && estate.getLocation() != null
                        && estate.getPrice() != null
                        && estate.getDevise() != null;
                this.handleStepsProgressBar(0, isCompleteMandatory);

                boolean isCompleteAddress = estate.getStreetNumberAndStreetName() != null
                        && estate.getZipCode() != null
                        && estate.getCountry() != null;
                this.handleStepsProgressBar(1, isCompleteAddress);
                this.handleStepsProgressBar(2, estate.getSurface() != null);
                this.handleStepsProgressBar(3, estate.getDescription() != null);
                this.handleStepsProgressBar(4, !estate.getMedia().isEmpty());
            }
        });

        this.sharedViewModel.getEstateSelectionId().observe(this.getViewLifecycleOwner(), estateSelectionId -> {
            this.detailsViewModel.fetchEstateToUpdateLiveData(estateSelectionId);
        });

        return root;
    }

    @Override
    public Estate getInitializedEstate() {
        return this.formViewModel.getEstateData();
    }

    private void handleStepsProgressBar(int stepIndex, boolean isComplete) {
        int colorSuccess = this.getResources().getColor(R.color.green);
        int colorUncomplete = this.getResources().getColor(R.color.orange);
        if(isComplete) {
            this.formStepsProgressBar.getChildAt(stepIndex).setBackgroundColor(colorSuccess);
        } else {
            this.formStepsProgressBar.getChildAt(stepIndex).setBackgroundColor(colorUncomplete);
        }
    }

}

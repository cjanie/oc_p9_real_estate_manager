package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.LocationActivity;
import com.openclassrooms.realestatemanager.ui.viewmodels.DetailsViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.DetailsViewModelFactory;

public class FormUpdateEstateFragment extends FormFragment {

    private DetailsViewModel detailsViewModel;

    public FormUpdateEstateFragment(LocationActivity locationActivity) {
        super(locationActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        this.sharedViewModel = new ViewModelProvider(this.requireActivity()).get(SharedViewModel.class);

        DetailsViewModelFactory detailsViewModelFactory = ((Launch)this.getActivity().getApplication()).detailsViewModelFactory();
        this.detailsViewModel = new ViewModelProvider(this.getActivity(), detailsViewModelFactory).get(DetailsViewModel.class);

        // Observe, listening to details view model
        this.detailsViewModel.getEstate().observe(this.getActivity(), estate -> {
            if(estate != null) {

                this.formViewModel.setEstateData(estate);
                this.handleProgressBar(estate);
            }
        });

        this.sharedViewModel.getEstateSelectionId().observe(this.getViewLifecycleOwner(), estateSelectionId -> {
            this.detailsViewModel.fetchEstateToUpdateLiveData(estateSelectionId);
        });

        return root;
    }

    private void handleProgressBar(Estate estate) {
        // is completeMandatory
        this.handleProgressBarStepMandatory(this.isCompleteMandatory(estate));

        // is completeAddress
        this.handleProgressBarStepAddress(this.isCompleteAddress(estate));

        // is complete description
        this.handleProgressBarStepDescription(this.isCompleteDescription(estate));

        // is completeDescriptionDetails
        this.handleProgressBarStepDescriptionDetails(this.isCompleteDescriptionDetails(estate));

        // is complete media
        this.handleProgressBarStepMedia(this.isCompleteMedia(estate));

        // is complete geolocation
        this.handleProgressBarStepGeolocation(this.isCompleteGeolocation(estate));
    }

    @Override
    public Estate getInitializedEstate() {
        return this.formViewModel.getEstateData();
    }

    @Override
    public void saveEstateDataUpdate() {

        Long id = this.formViewModel.saveEstateDataUpdate();

        // For geocoding request worker
        if(id != null && id > 0) {
            if(this.getData().getLatitude() == null || this.getData().getLongitude() == null) {
                if (this.isCompleteAddress(this.getData())) {
                    this.saveIdForGeocodingRequestWorker(id);
                }
            }
        }
    }

}

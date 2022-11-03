package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.Action;
import com.openclassrooms.realestatemanager.ui.viewmodels.FormViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.FormViewModelFactory;

import java.util.List;

public abstract class FormFragment extends BaseFragment implements
        FormMandatoryFieldsFragment.HandleFormMandatoryFields,
        FormAddressFragment.HandleAddressFields,
        FormDescriptionDetailsFragment.HandleDescriptionDetailsData,
        FormDescriptionFragment.HandleDescriptionData,
        FormMediaFragment.HandleMediaData,
        SaveEstateDataUpdate,
        Next,
        FormData {

    private final int LAYOUT_ID = R.layout.fragment_form;

    protected LinearLayout formStepsProgressBar;

    private final int NUMBER_OF_STEPS = 5;

    protected FormViewModel formViewModel;

    protected SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);
        this.formStepsProgressBar = root.findViewById(R.id.layout_form_steps_progress_bar);

        for(int i=0; i<this.NUMBER_OF_STEPS; i++) {
            View view = inflater.inflate(R.layout.layout_form_step_checked_icon, this.formStepsProgressBar, false);
            this.formStepsProgressBar.addView(view);
        }


        FormViewModelFactory formViewModelFactory = ((Launch)this.getActivity().getApplication()).formViewModelFactory();
        this.formViewModel = new ViewModelProvider(this, formViewModelFactory).get(FormViewModel.class);
        this.sharedViewModel = new ViewModelProvider(this.requireActivity()).get(SharedViewModel.class);

        FormMandatoryFieldsFragment formMandatoryFieldsFragment = new FormMandatoryFieldsFragment(
                this,
                this,
                this,
                this
        );
        this.showFragment(formMandatoryFieldsFragment);

        return root;
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame_layout_form, fragment).commit();
    }

    @Override
    public void setMandatoryFields(Estate estate) {
        // Mandatory properties should not be null
        if(estate.getType() != null
                && estate.getLocation() != null
                && estate.getPrice() != null
                && estate.getDevise() != null) {

            this.formViewModel.setEstateDataMandatory(
                    estate.getType(), estate.getLocation(), estate.getPrice(), estate.getDevise()
            );
        };
    }

    @Override
    public void setEstateAdressData(String streetNumberAndName, String addressComplements, String zipCode, String country) {
        this.formViewModel.setEstateDataAddress(streetNumberAndName, addressComplements, zipCode, country);
    }

    @Override
    public void setDescriptionDetailsData(Integer surface, Integer numberOfRooms, Integer numberOfBathrooms, Integer numberOfBedrooms) {
        this.formViewModel.setEstateDataDescriptionDetails(surface, numberOfRooms, numberOfBathrooms, numberOfBedrooms);
    }

    @Override
    public void saveEstateDataUpdate() {
        this.formViewModel.saveEstateDataUpdate();
    }

    @Override
    public void next(Fragment actualFragment) {
        if(actualFragment instanceof FormMandatoryFieldsFragment) {
            FormAddressFragment addressFragment = new FormAddressFragment(
                    this, this, this, this
            );
            this.showFragment(addressFragment);

        } else if(actualFragment instanceof FormAddressFragment) {
            FormDescriptionDetailsFragment descriptionDetailsFragment = new FormDescriptionDetailsFragment(
                    this,
                    this,
                    this,
                    this
            );
            this.showFragment(descriptionDetailsFragment);

        } else if(actualFragment instanceof FormDescriptionDetailsFragment) {
            FormDescriptionFragment descriptionFragment = new FormDescriptionFragment(
                    this,
                    this,
                    this,
                    this
            );
            this.showFragment(descriptionFragment);

        } else if(actualFragment instanceof FormDescriptionFragment) {
            FormMediaFragment mediaFragment = new FormMediaFragment(
                    this,this, this, this
            );
            this.showFragment(mediaFragment);

        } else if(actualFragment instanceof FormMediaFragment) {
            this.sharedViewModel.updateAction(Action.HOME);
        }

    }

    @Override
    public Estate getData() {
        return this.formViewModel.getEstateData();
    }

    @Override
    public void setEstateDescriptionData(String description) {
        this.formViewModel.setEstateDataDescription(description);
    }

    @Override
    public void setEstateMediaData(List<String> media) {
        this.formViewModel.setEstateDataMedia(media);
    }
}

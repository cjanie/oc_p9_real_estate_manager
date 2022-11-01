package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public abstract class FormFragment extends BaseFragment implements
        FormMandatoryFieldsFragment.HandleFormMandatoryFields,
        FormAddressFragment.HandleAddressFields,
        FormDescriptionDetailsFragment.HandleDescriptionDetailsData,
        SaveEstateDataUpdate,
        Next,
        FormData {

    private final int LAYOUT_ID = R.layout.fragment_form;

    protected FormViewModel formViewModel;

    protected SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);

        FormViewModelFactory formViewModelFactory = ((Launch)this.getActivity().getApplication()).formViewModelFactory();
        this.formViewModel = new ViewModelProvider(this, formViewModelFactory).get(FormViewModel.class);
        this.sharedViewModel = new ViewModelProvider(this.requireActivity()).get(SharedViewModel.class);

        FormMandatoryFieldsFragment formMandatoryFieldsFragment = new FormMandatoryFieldsFragment(this, this);
        this.showFragment(formMandatoryFieldsFragment);

        return root;
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame_layout_form, fragment).commit();
    }

    @Override
    public void saveMandatory(Estate estate) {
        // Mandatory properties should not be null
        if(estate.getType() != null
                && estate.getLocation() != null
                && estate.getPrice() != null
                && estate.getDevise() != null) {

            this.formViewModel.setEstateDataMandatory(
                    estate.getType(), estate.getLocation(), estate.getPrice(), estate.getDevise()
            );
            this.formViewModel.saveEstateDataUpdate();
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
            this.sharedViewModel.updateAction(Action.HOME);
        }

    }

    @Override
    public Estate getData() {
        return this.formViewModel.getEstateData();
    }
}

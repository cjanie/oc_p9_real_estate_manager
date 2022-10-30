package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.viewmodels.FormViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.FormViewModelFactory;

public abstract class FormFragment extends BaseFragment implements FormMandatoryFieldsFragment.HandleFormMandatoryFields {

    private final int LAYOUT_ID = R.layout.fragment_form;

    protected FormViewModel formViewModel;

    private LinearLayout formOptionalLayout;

    private ConstraintLayout formAddressLayout;

    protected EditText streetNumberAndStreetName;

    protected EditText addressComplements;

    protected EditText zipCode;

    protected EditText country;


    private ConstraintLayout descriptionDetailsLayout;

    protected EditText surface;

    protected EditText numberOfRooms;

    protected EditText numberOfBathrooms;

    protected EditText numberOfBedrooms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);

        FormViewModelFactory formViewModelFactory = ((Launch)this.getActivity().getApplication()).formViewModelFactory();
        this.formViewModel = new ViewModelProvider(this, formViewModelFactory).get(FormViewModel.class);

        FormMandatoryFieldsFragment formMandatoryFieldsFragment = new FormMandatoryFieldsFragment(this);
        this.showFragment(formMandatoryFieldsFragment);

        this.formOptionalLayout = root.findViewById(R.id.layout_not_mandatory_fields);

        this.formAddressLayout = root.findViewById(R.id.layout_form_address);
        this.streetNumberAndStreetName = root.findViewById(R.id.editText_streetNumber_and_streetName);
        this.addressComplements = root.findViewById(R.id.editText_addressComplements);
        this.zipCode = root.findViewById(R.id.editText_zipCode);
        this.country = root.findViewById(R.id.editText_country);

        this.descriptionDetailsLayout = root.findViewById(R.id.layout_form_description_details);
        this.surface = root.findViewById(R.id.editText_surface);
        this.numberOfRooms = root.findViewById(R.id.editText_numberOfRooms);
        this.numberOfBathrooms = root.findViewById(R.id.editText_numberOfBathrooms);
        this.numberOfBedrooms = root.findViewById(R.id.editText_numberOfBedrooms);

        return root;
    }

    @Override
    public void save(Estate estate) {
        // Mandatory properties should not be null
        if(estate.getType() != null
                && estate.getLocation() != null
                && estate.getPrice() != null
                && estate.getDevise() != null) {
            this.formViewModel.saveEstate(estate);
        };
    }


    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame_layout_form, fragment).commit();
    }







}

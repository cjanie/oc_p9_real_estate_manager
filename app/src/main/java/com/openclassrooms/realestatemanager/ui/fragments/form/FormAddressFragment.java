package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.fragments.Next;

import java.util.HashMap;
import java.util.Map;

public class FormAddressFragment extends FormOnTextChangedFragment implements
        View.OnClickListener {

    private final int LAYOUT_ID = R.layout.fragment_form_address;

    private HandleAddressFields handleAddressFields;

    private EditText streetNumberAndStreetName;

    // Views
    private EditText addressComplements;

    protected EditText zipCode;

    private EditText country;

    private Button save;

    private Button skip;

    // Constructor
    public FormAddressFragment(
            SaveEstateDataUpdate saveEstateDataUpdate,
            Next next,
            FormData formData,
            HandleAddressFields handleAddressFields
    ) {
        super(saveEstateDataUpdate, next, formData);
        this.handleAddressFields = handleAddressFields;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);
        this.streetNumberAndStreetName = root.findViewById(R.id.editText_streetNumber_and_streetName);
        this.addressComplements = root.findViewById(R.id.editText_addressComplements);
        this.zipCode = root.findViewById(R.id.editText_zipCode);
        this.country = root.findViewById(R.id.editText_country);
        this.save = root.findViewById(R.id.button_save_form_address);
        this.skip = root.findViewById(R.id.button_skip_form_address);

        this.streetNumberAndStreetName.addTextChangedListener(this);
        this.addressComplements.addTextChangedListener(this);
        this.zipCode.addTextChangedListener(this);
        this.country.addTextChangedListener(this);

        String location = this.getFormData().getLocation();
        this.autoCompleteZipCode(location);
        this.autoCompleteCountry(location);

        this.save.setOnClickListener(this);
        this.skip.setOnClickListener(this);

        Estate currentEstate = this.getFormData();
        if(currentEstate != null) {
            if(currentEstate.getStreetNumberAndStreetName() != null) {
                this.streetNumberAndStreetName.setText(currentEstate.getStreetNumberAndStreetName());
            }
            if(currentEstate.getAddressComplements() != null) {
                this.addressComplements.setText(currentEstate.getAddressComplements());
            }
            if(currentEstate.getZipCode() != null) {
                this.zipCode.setText(currentEstate.getZipCode());
            }
            if(currentEstate.getCountry() != null) {
                this.country.setText(currentEstate.getCountry());
            }
        }

        return root;
    }

    private void autoCompleteZipCode(String location) {
        if(TextUtils.isEmpty(this.zipCode.getText())) {
            Map<String, String> locationToZipCodeMap = new HashMap<>();
            locationToZipCodeMap.put("Paris", "75000");
            locationToZipCodeMap.put("Montpellier", "34000");
            locationToZipCodeMap.put("Orange", "84000");
            this.zipCode.setText(locationToZipCodeMap.get(location));
        }
    }

    private void autoCompleteCountry(String location) {
        if(TextUtils.isEmpty(this.country.getText())) {
            Map<String, String> locationToCountryMap = new HashMap<>();
            locationToCountryMap.put("Paris", "France");
            locationToCountryMap.put("Montpellier", "France");
            locationToCountryMap.put("Orange", "France");
            locationToCountryMap.put("Manchester", "England");
            this.country.setText(locationToCountryMap.get(location));
        }
    }

    @Override
    protected void enableButtonSave() {
        this.enableButton(this.save);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_save_form_address) {
            this.save();
            this.next();

        } else if(view.getId() == R.id.button_skip_form_address) {
            this.next();
        }
    }

    @Override
    protected void save() {
        String streetNumberAndName = null;
        String addressCompl = null;
        String zip = null;
        String coutryName = null;
        if(!TextUtils.isEmpty(streetNumberAndStreetName.getText())) {
            streetNumberAndName = streetNumberAndStreetName.getText().toString();
        }
        if(!TextUtils.isEmpty(addressComplements.getText())) {
            addressCompl = addressComplements.getText().toString();
        }
        if(!TextUtils.isEmpty(zipCode.getText())) {
            zip = zipCode.getText().toString();
        }
        if(!TextUtils.isEmpty(country.getText())) {
            coutryName = country.getText().toString();
        }

        this.handleAddressFields.setEstateAdressData(streetNumberAndName, addressCompl, zip, coutryName);
        this.saveEstateDataUpdate.saveEstateDataUpdate();
    }

    @Override
    public FormStepEnum getCurrentStep() {
        return FormStepEnum.ADDRESS;
    }


    interface HandleAddressFields {
        void setEstateAdressData(String streetNumberAndName, String addressComplements, String zipCode, String country);
    }
}

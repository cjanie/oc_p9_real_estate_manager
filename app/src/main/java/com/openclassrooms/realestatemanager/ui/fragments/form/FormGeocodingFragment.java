package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.ui.fragments.Next;

public class FormGeocodingFragment extends FormSaveSkipFragment implements View.OnClickListener {

    private HandleGeocodingData handleGeocodingData;

    private Button skip;
    private Button save;

    public FormGeocodingFragment(SaveEstateDataUpdate saveEstateDataUpdate, Next next, FormData formData, HandleGeocodingData handleGeocodingData) {
        super(saveEstateDataUpdate, next, formData);
        this.handleGeocodingData = handleGeocodingData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_form_geocoding, container, false);
        this.skip = root.findViewById(R.id.button_skip_form);
        this.save = root.findViewById(R.id.button_save_form);

        this.skip.setOnClickListener(this);
        this.save.setOnClickListener(this);
        return root;
    }

    @Override
    protected void save() {
        Geolocation geolocation = this.requestGeocodingData();
        this.handleGeocodingData.setEstateGeocodingData(geolocation.getLatitude(), geolocation.getLongitude());
        this.saveEstateDataUpdate.saveEstateDataUpdate();
    }

    private Geolocation requestGeocodingData() {
        Double latitude = 0.0; // TODO Geocoding request via view model
        Double longitude = 0.0;
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(latitude);
        geolocation.setLongitude(longitude);
        return geolocation;
    }

    @Override
    public FormStepEnum getCurrentStep() {
        return FormStepEnum.GEOCODING;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.button_save_form) {
            this.save();
            this.next();
        } else {
            this.next();
        }
    }

    public interface HandleGeocodingData {
        void setEstateGeocodingData(double latitude, double longitude);
    }
}

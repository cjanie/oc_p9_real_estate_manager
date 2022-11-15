package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.LocationActivity;
import com.openclassrooms.realestatemanager.ui.fragments.Next;

public class FormGeolocationPermissionFragmentForm extends FormLocationPermissionFragment implements View.OnClickListener {

    private final int LAYOUT_ID = R.layout.fragment_geolocation;

    private TextView latitude;
    private TextView longitude;

    private Button geolocalize;

    private Button save;
    private Button skip;

    private HandleGeolocation handleGeolocation;

    public FormGeolocationPermissionFragmentForm(
            SaveEstateDataUpdate saveEstateDataUpdate,
            Next next,
            FormData formData,
            HandleGeolocation handleGeolocation,
            LocationActivity locationActivity) {
        super(saveEstateDataUpdate, next, formData, locationActivity);
        this.handleGeolocation = handleGeolocation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);
        this.latitude = root.findViewById(R.id.form_latitude);
        this.longitude = root.findViewById(R.id.form_longitude);
        this.geolocalize = root.findViewById(R.id.button_geolocalize);
        this.save = root.findViewById(R.id.button_save_form);
        this.skip = root.findViewById(R.id.button_skip_form);

        this.geolocalize.setOnClickListener(this);
        this.save.setOnClickListener(this);
        this.skip.setOnClickListener(this);

        return root;
    }

    @Override
    protected void displayMyPosition(Double lat, Double longit) {
        this.latitude.setText(lat.toString());
        this.longitude.setText(longit.toString());
        this.enableButton(save);
    }

    @Override
    protected void save() {
        if(this.myPosition != null) {
            this.handleGeolocation.setGeolocation(this.myPosition.getLatitude(), this.myPosition.getLongitude());
            this.saveEstateDataUpdate.saveEstateDataUpdate();
        }
    }

    @Override
    public FormStepEnum getCurrentStep() {
        return FormStepEnum.GEOLOCATION;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_geolocalize) {
            this.launchLocationPermissionRequest();
        } else if(view.getId() == R.id.button_save_form) {
            this.save();
            this.next();
        } else if(view.getId() == R.id.button_skip_form) {
            this.next();
        }
    }

    interface HandleGeolocation {
        void setGeolocation(Double latitude, Double longitude);
    }
}

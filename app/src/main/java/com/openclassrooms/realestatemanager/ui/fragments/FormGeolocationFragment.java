package com.openclassrooms.realestatemanager.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.realestatemanager.R;

public class FormGeolocationFragment extends FormSaveSkipFragment implements View.OnClickListener {

    private final int LAYOUT_ID = R.layout.fragment_geolocation;
    private TextView latitude;
    private TextView longitude;

    private Button geolocalize;

    private Button save;
    private Button skip;

    private HandleGeolocation handleGeolocation;

    private Location location;

    private String PERMISSION_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    private ActivityResultLauncher launcherRequestLocation = this.registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isPermissionGranted -> {
                this.handleLocationPermissionIsGranted();
            }
    );

    public FormGeolocationFragment(
            SaveEstateDataUpdate saveEstateDataUpdate,
            Next next,
            FormData formData,
            HandleGeolocation handleGeolocation) {
        super(saveEstateDataUpdate, next, formData);
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

    @SuppressLint("MissingPermission")
    private void handleLocationPermissionIsGranted() {
        // Get location when permission is not missing
        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this.getActivity());

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (!locationResult.getLocations().isEmpty()) {
                    stopLocationUpdates(fusedLocationProviderClient, this);
                    Location locationFromResult = locationResult.getLocations().get(0);
                    Double lat = locationFromResult.getLatitude();
                    latitude.setText(lat.toString());
                    Double longi = locationFromResult.getLongitude();
                    longitude.setText(longi.toString());
                    location = locationFromResult;
                    enableButton(save);
                }
            }

            @Override
            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                if (locationAvailability.isLocationAvailable()) {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(geolocation ->
                            location = geolocation
                    );
                } else {
                    requestLocationUpdates(fusedLocationProviderClient, this);
                }

            }
        };
        requestLocationUpdates(fusedLocationProviderClient, locationCallback);
    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdates(
            FusedLocationProviderClient fusedLocationProviderClient,
            LocationCallback locationCallback
    ) {
        LocationRequest locationRequest = new LocationRequest.Builder(100).build();
        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
        );
    }

    private void stopLocationUpdates(
            FusedLocationProviderClient fusedLocationProviderClient,
            LocationCallback locationCallback
    ) {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void save() {
        if(this.location != null) {
            this.handleGeolocation.setGeolocation(this.location.getLatitude(), this.location.getLongitude());
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
            this.launcherRequestLocation.launch(this.PERMISSION_LOCATION);
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

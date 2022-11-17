package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Looper;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.openclassrooms.realestatemanager.ui.LocationActivity;
import com.openclassrooms.realestatemanager.ui.fragments.Next;

public abstract class FormLocationPermissionFragment extends FormSaveSkipFragment {

    private LocationActivity locationActivity;

    protected Location myPosition;

    private ActivityResultLauncher launcherLocationPermission = this.registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isPermissionGranted -> {
                this.handleLocationPermissionIsGranted();
            }
    );

    public FormLocationPermissionFragment(SaveEstateDataUpdate saveEstateDataUpdate, Next next, FormData formData, LocationActivity locationActivity) {
        super(saveEstateDataUpdate, next, formData);
        this.locationActivity = locationActivity;
    }

    protected void launchLocationPermissionRequest() {
        this.locationActivity.launchLocationPermissionRequest(this.launcherLocationPermission);
    }

    @SuppressLint("MissingPermission")
    protected void handleLocationPermissionIsGranted() {
        // Get location when permission is not missing
        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this.getActivity());

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (!locationResult.getLocations().isEmpty()) {
                    locationActivity.stopLocationUpdates(fusedLocationProviderClient, this);
                    myPosition = locationResult.getLocations().get(0);
                    displayMyPosition(myPosition.getLatitude(), myPosition.getLongitude());
                }
            }

            @Override
            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                if (locationAvailability.isLocationAvailable()) {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(
                            location -> myPosition = location
                    );
                } else {
                    locationActivity.requestLocationUpdates(fusedLocationProviderClient, this);
                }
            }

        };
        this.locationActivity.requestLocationUpdates(fusedLocationProviderClient, locationCallback);
    }

    protected abstract void displayMyPosition(Double latitude, Double longitude);

}

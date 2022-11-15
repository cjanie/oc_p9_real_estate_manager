package com.openclassrooms.realestatemanager.ui;

import android.Manifest;
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
import com.openclassrooms.realestatemanager.ui.fragments.BaseFragment;
import com.openclassrooms.realestatemanager.ui.fragments.Next;
import com.openclassrooms.realestatemanager.ui.fragments.form.FormData;
import com.openclassrooms.realestatemanager.ui.fragments.form.FormSaveSkipFragment;
import com.openclassrooms.realestatemanager.ui.fragments.form.SaveEstateDataUpdate;

public abstract class LocationFragment extends FormSaveSkipFragment {

    private String PERMISSION_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    protected Location myPosition;

    private ActivityResultLauncher launcherRequestLocation = this.registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isPermissionGranted -> {
                this.handleLocationPermissionIsGranted();
            }
    );

    public LocationFragment(SaveEstateDataUpdate saveEstateDataUpdate, Next next, FormData formData) {
        super(saveEstateDataUpdate, next, formData);
    }

    protected void launchLocationPermissionRequest() {
        this.launcherRequestLocation.launch(this.PERMISSION_LOCATION);
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
                    stopLocationUpdates(fusedLocationProviderClient, this);
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
                    requestLocationUpdates(fusedLocationProviderClient, this);
                }
            }

        };
        requestLocationUpdates(fusedLocationProviderClient, locationCallback);
    }

    protected abstract void displayMyPosition(Double latitude, Double longitude);

    @SuppressLint("MissingPermission")
    protected void requestLocationUpdates(
            FusedLocationProviderClient fusedLocationProviderClient,
            LocationCallback locationCallback
    ) {
        LocationRequest locationRequest = new LocationRequest.Builder(100).build();
        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
        );
    }

    protected void stopLocationUpdates(
            FusedLocationProviderClient fusedLocationProviderClient,
            LocationCallback locationCallback
    ) {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


}

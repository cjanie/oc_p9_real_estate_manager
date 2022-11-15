package com.openclassrooms.realestatemanager.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Looper;

import androidx.activity.result.ActivityResultLauncher;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

public class LocationActivity extends BaseActivity {

    private String PERMISSION_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    public void launchLocationPermissionRequest(ActivityResultLauncher launcher) {
        launcher.launch(this.PERMISSION_LOCATION);
    }


    @SuppressLint("MissingPermission")
    public void requestLocationUpdates(
            FusedLocationProviderClient fusedLocationProviderClient,
            LocationCallback locationCallback
    ) {
        LocationRequest locationRequest = new LocationRequest.Builder(100).build();
        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
        );
    }

    public void stopLocationUpdates(
            FusedLocationProviderClient fusedLocationProviderClient,
            LocationCallback locationCallback
    ) {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}

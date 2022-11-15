package com.openclassrooms.realestatemanager.ui.fragments;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.LocationActivity;
import com.openclassrooms.realestatemanager.ui.viewmodels.EstatesViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.EstatesViewModelFactory;

public class MapEstatesFragment extends MapsFragment {

    private EstatesViewModel estatesViewModel;

    private LocationActivity locationActivity;

    private Location myPosition;

    private GoogleMap map;


    private ActivityResultLauncher launcherLocationPermission = this.registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isPermissionGranted -> {
                this.handleLocationPermissionIsGranted();
            }
    );

    public MapEstatesFragment(LocationActivity locationActivity) {
        this.locationActivity = locationActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EstatesViewModelFactory estatesViewModelFactory = ((Launch) this.getActivity().getApplication()).estatesViewModelFactory();
        this.estatesViewModel = new ViewModelProvider(this.getActivity(), estatesViewModelFactory).get(EstatesViewModel.class);
    }

    @Override
    protected void updateMap(GoogleMap googleMap) {

        // Put the markers on the map observing list from the view model
        this.estatesViewModel.getEstates().observe(this.getViewLifecycleOwner(), estates -> {
            if(!estates.isEmpty()) {
                for(Estate estate: estates) {
                    if(estate.getLatitude() != null && estate.getLongitude() != null) {
                        LatLng position = new LatLng(estate.getLatitude(), estate.getLongitude());
                        String title = estate.getStreetNumberAndStreetName() != null ? estate.getStreetNumberAndStreetName() : "";
                        googleMap.addMarker(new MarkerOptions().position(position).title(title));
                    }
                }
            }
        });
        this.estatesViewModel.fetchEstatesToUpdateLiveData();
        this.map = googleMap;
        // Enable my position
        this.locationActivity.launchLocationPermissionRequest(this.launcherLocationPermission);

        this.map.setMinZoomPreference(6.0f);
        this.map.setMaxZoomPreference(14.0f);
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
                    locationActivity.stopLocationUpdates(fusedLocationProviderClient, this);
                    myPosition = locationResult.getLocations().get(0);
                    displayMyPosition();
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

    @SuppressLint("MissingPermission")
    private void displayMyPosition() {
        this.map.setMyLocationEnabled(true);
        this.map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(myPosition.getLatitude(), myPosition.getLongitude())));

    }

}


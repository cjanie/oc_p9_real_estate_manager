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
import com.openclassrooms.realestatemanager.ui.exceptions.GeolocationException;
import com.openclassrooms.realestatemanager.ui.exceptions.WifiException;
import com.openclassrooms.realestatemanager.ui.viewmodels.EstatesViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.EstatesViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MapEstatesFragment extends MapWifiFragment {

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
        this.estatesViewModel = new ViewModelProvider(this, estatesViewModelFactory).get(EstatesViewModel.class);
    }

    @Override
    protected void updateMap(GoogleMap googleMap) {

        // Put the markers on the map observing list from the view model
        this.estatesViewModel.getEstates().observe(this.getViewLifecycleOwner(), estates -> {
            try {
                this.setUpMap(estates, googleMap);

            } catch (WifiException e) {
                e.printStackTrace();
                this.setWifiEnabled(true);

                // TODO call back on connectivity result
                // OK > this.setUpMap(estates, googleMap);
                // Not OK > this.onBackPressed
            }
        });
        this.estatesViewModel.fetchEstatesToUpdateLiveData();
        this.map = googleMap;
        // Enable my position
        this.locationActivity.launchLocationPermissionRequest(this.launcherLocationPermission);

        this.map.setMinZoomPreference(6.0f);
        this.map.setMaxZoomPreference(14.0f);
    }

    private void setUpMap(List<Estate> estates, GoogleMap googleMap) throws WifiException {
        try {
            List<Estate> geolocalizedEstates = this.getGeolocalizedEstates(estates);
            if(!geolocalizedEstates.isEmpty()) {
                for(Estate estate: geolocalizedEstates) {
                    LatLng position = new LatLng(estate.getLatitude(), estate.getLongitude());
                    String title = estate.getStreetNumberAndStreetName() != null ? estate.getStreetNumberAndStreetName() : "";
                    googleMap.addMarker(new MarkerOptions().position(position).title(title));
                }
            }
        } catch (GeolocationException e) {
            e.printStackTrace();
        }
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

    private List<Estate> getGeolocalizedEstates(List<Estate> estates) throws WifiException, GeolocationException {
        if(isWifiAvailable()) {
            // TODO from the list of estates : geolocalise each estate from address
            return this.geolocalizeEstates(estates);
        } else {
            throw new WifiException();
        }
    }

    private List<Estate> geolocalizeEstates(List<Estate> estates) throws GeolocationException {
        List<Estate> geolocalizedEstates = new ArrayList<>();
        if(!estates.isEmpty()) {
            for(Estate estate: estates) {
                if(estate.getStreetNumberAndStreetName() != null) {
                    // TODO geolocalise with api from number and street name and town
                    Double latitude = 0.0; // TODO api result
                    Double longitude = 0.0; // TODO api result
                    if(latitude != null && longitude != null) {
                        Estate geolocalizedEstate = this.geolocalizeEstate(estate);
                        geolocalizedEstates.add(geolocalizedEstate);
                    }
                }
            }
        }
        return geolocalizedEstates;
    }

    private Estate geolocalizeEstate(Estate estate) throws GeolocationException {
        if(estate.getStreetNumberAndStreetName() != null) {
            // TODO geolocalise with api from number and street name and town
            Double latitude = 0.0; // TODO api result
            Double longitude = 0.0; // TODO api result
            if(latitude != null && longitude != null) {
                Estate geolocalizedEstate = estate;
                geolocalizedEstate.setLatitude(latitude);
                geolocalizedEstate.setLongitude(longitude);
                return geolocalizedEstate;
            }
        }
        throw new GeolocationException();
    }

}


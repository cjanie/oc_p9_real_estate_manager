package com.openclassrooms.realestatemanager.ui.fragments;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

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
import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.ui.LocationActivity;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.GeolocationException;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.exceptions.PayloadException;
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
                this.setUpMap(estates);

            } catch (WifiException e) {
                e.printStackTrace();
                this.setWifiEnabled(true);
                Toast.makeText(this.getContext(), e.getClass().getName(), Toast.LENGTH_LONG).show();

                // TODO call back on connectivity result
                // OK > this.setUpMap(estates, googleMap);
                // Not OK > this.onBackPressed
            } catch (PayloadException | GeolocationException e) {
                e.printStackTrace();
                Toast.makeText(this.getContext(), e.getClass().getName(), Toast.LENGTH_LONG).show();

            }
        });
        this.estatesViewModel.fetchEstatesToUpdateLiveData();

        this.geolocationViewModel.getGeolocalizedEstates().observe(this.getViewLifecycleOwner(), estates -> {
            if(!estates.isEmpty()) {
                for(Estate estate: estates) {
                    this.addMarker(estate);
                }

            }
        });

        this.map = googleMap;
        // Enable my position
        this.locationActivity.launchLocationPermissionRequest(this.launcherLocationPermission);

        this.map.setMinZoomPreference(6.0f);
        this.map.setMaxZoomPreference(14.0f);
    }

    private void addMarker(Estate estate) {
        this.map.addMarker(new MarkerOptions()
                .position(new LatLng(estate.getLatitude(), estate.getLongitude())))
                .setTitle(estate.getStreetNumberAndStreetName() != null ? estate.getStreetNumberAndStreetName() : "");
    }

    private void setUpMap(List<Estate> estates) throws WifiException, PayloadException, GeolocationException {
        if(!estates.isEmpty()) {

            List<Estate> ungeolocalised = new ArrayList<>();

            for(Estate e: estates) {
                if(e.getLatitude() != null && e.getLongitude() != null) {
                    this.addMarker(e);
                } else {
                    if(e.getStreetNumberAndStreetName() != null && e.getLocation() != null && e.getCountry() != null) {
                        ungeolocalised.add(e);
                    }
                }
            }
            this.geolocationViewModel.fetchGeolocationsToUpdateLiveData(ungeolocalised);


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

}


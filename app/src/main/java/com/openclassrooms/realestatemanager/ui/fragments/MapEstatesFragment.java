package com.openclassrooms.realestatemanager.ui.fragments;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.viewmodels.EstatesViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.EstatesViewModelFactory;

import java.util.List;

public class MapEstatesFragment extends MapsFragment {

    private EstatesViewModel estatesViewModel;

    private String PERMISSION_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    private ActivityResultLauncher launcherRequestLocation = this.registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isPermissionGranted -> {
                this.handleLocationPermissionIsGranted();
            }
    );


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EstatesViewModelFactory estatesViewModelFactory = ((Launch) this.getActivity().getApplication()).estatesViewModelFactory();
        this.estatesViewModel = new ViewModelProvider(this.getActivity(), estatesViewModelFactory).get(EstatesViewModel.class);
    }

    @Override
    protected void updateMap(GoogleMap googleMap) {
        // TODO enable my position

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
        googleMap.setMinZoomPreference(6.0f);
        googleMap.setMaxZoomPreference(14.0f);
    }

    void handleLocationPermissionIsGranted() {

    }

}

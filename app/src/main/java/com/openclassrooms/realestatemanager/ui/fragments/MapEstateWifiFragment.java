package com.openclassrooms.realestatemanager.ui.fragments;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;

public class MapEstateWifiFragment extends MapWifiFragment {

    private final Estate estate;

    public MapEstateWifiFragment(Estate estate) {
        this.estate = estate;
    }

    @Override
    protected void fetchGeolocationDataWhenWifiEnabled() {

        this.geocodingViewModel.fetchGeolocationsToUpdateLiveData(this.estate);
        this.geocodingViewModel.fetchGeolocationResultsToUpdateLiveData(this.estate);

    }
    @Override
    protected void updateMap(GoogleMap googleMap) {

        this.geocodingViewModel.getGeolocationResults().observe(this.getViewLifecycleOwner(),
                geolocations -> {
                    if(!geolocations.isEmpty()) {
                        for(int i=0; i<geolocations.size(); i++) {
                            LatLng position = new LatLng(geolocations.get(i).getLatitude(), geolocations.get(i).getLongitude());
                            String title = String.valueOf(i);
                            googleMap.addMarker(new MarkerOptions().position(position).title(title));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                        }
                    }
                });

        if(this.estate.getLatitude() != null && this.estate.getLongitude() != null) {

            LatLng position = new LatLng(this.estate.getLatitude(), this.estate.getLongitude());
            String title = estate.getStreetNumberAndStreetName() != null ? estate.getStreetNumberAndStreetName() : "";
            googleMap.addMarker(new MarkerOptions().position(position).title(title));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));

        } else {
            this.checkWifiToFetchGeolocationData();
        }

        googleMap.setMinZoomPreference(6.0f);
        googleMap.setMaxZoomPreference(14.0f);
    }
}

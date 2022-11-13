package com.openclassrooms.realestatemanager.ui.fragments;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDetailsFragment extends MapsFragment {

    private Double latitude;

    private Double longitude;

    private String placeName;

    public MapDetailsFragment(Double latitude, Double longitude, String placeName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
    }

    @Override
    protected GoogleMap addMarker(GoogleMap googleMap) {
        if(this.latitude != null && this.longitude != null) {
            LatLng position = new LatLng(latitude, longitude);
            String title = placeName != null ? placeName : "";
            googleMap.addMarker(new MarkerOptions().position(position).title(title));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));

            googleMap.setMinZoomPreference(6.0f);
            googleMap.setMaxZoomPreference(14.0f);
        }
        return googleMap;
    }
}

package com.openclassrooms.realestatemanager.data.webservices.mapapi;

import com.openclassrooms.realestatemanager.BuildConfig;

public class GoogleMapsRequestConfig {

    // https://developers.google.com/maps/documentation/javascript/geocoding
    // /maps/api/geocode/json


    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/";

    public static final String API_KEY = BuildConfig.GOOGLE_PLACE_API_KEY;

    public static final String GEOCODE_ENDPOINT = "geocode/json";
}

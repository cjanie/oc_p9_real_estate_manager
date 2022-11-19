package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import android.os.Build;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.entities.Geolocation;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.GeolocationGateway;
import com.openclassrooms.realestatemanager.ui.exceptions.GeolocationException;

import java.util.Arrays;

public class GeolocationGatewayImpl implements GeolocationGateway {

    GeoApiContext geoApiContext;

    private final String apiKey = BuildConfig.GOOGLE_PLACE_API_KEY;

    public GeolocationGatewayImpl() {
        this.geoApiContext = new GeoApiContext();
        this.geoApiContext.setApiKey(this.apiKey);
    }

    @Override
    public Geolocation geolocalize(String address) throws GeolocationException {

        GeocodingApiRequest request = GeocodingApi.newRequest(geoApiContext)
                .address(address);

        try {
            request.await();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Arrays.stream(request.await()).map(geocodingResult -> {
                    Geolocation geolocation = new Geolocation(
                            geocodingResult.geometry.location.lat,
                            geocodingResult.geometry.location.lng);
                    return geolocation;
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeolocationException();
        }
        throw new GeolocationException();
    }

}

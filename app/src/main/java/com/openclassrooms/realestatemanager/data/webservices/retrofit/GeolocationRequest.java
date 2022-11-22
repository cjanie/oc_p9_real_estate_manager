package com.openclassrooms.realestatemanager.data.webservices.retrofit;

import com.openclassrooms.realestatemanager.data.webservices.mapapi.GoogleMapsRequestConfig;
import com.openclassrooms.realestatemanager.data.webservices.mapapi.deserializers.GeolocationResponseRoot;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeolocationRequest {

    @GET(GoogleMapsRequestConfig.GEOCODE_ENDPOINT)
    Observable<GeolocationResponseRoot> getData(
            @Query("address") String address,
            @Query("key") String key
    );
}

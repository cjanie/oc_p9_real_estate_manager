package com.openclassrooms.realestatemanager.data.webservices.retrofit;

import com.openclassrooms.realestatemanager.data.webservices.mapapi.GoogleMapsRequestConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleMapsHttpClientProvider {

    private Retrofit retrofit;

    public GoogleMapsHttpClientProvider() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(GoogleMapsRequestConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return this.retrofit;
    }
}

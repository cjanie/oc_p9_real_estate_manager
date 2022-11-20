package com.openclassrooms.realestatemanager.data.webservices.mapapi.deserializers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.model.GeocodingResult;

import java.util.List;

public class GeolocationResponseRoot {

    @SerializedName("results")
    @Expose
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}

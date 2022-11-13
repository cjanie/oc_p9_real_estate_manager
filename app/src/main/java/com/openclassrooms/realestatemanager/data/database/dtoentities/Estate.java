package com.openclassrooms.realestatemanager.data.database.dtoentities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.openclassrooms.realestatemanager.businesslogic.enums.EstateStatus;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.data.database.typeconverters.EstateStatusConverter;
import com.openclassrooms.realestatemanager.data.database.typeconverters.EstateTypeConverter;
import com.openclassrooms.realestatemanager.data.database.typeconverters.MediaConverter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Estate {

    @PrimaryKey
    private Integer id;

    @TypeConverters(EstateTypeConverter.class)
    private EstateType type;

    private String location;

    private Float priceInDollars;

    private Integer surfaceInMeterSquare;

    private Integer numberOfRooms;

    private Integer numberOfBathrooms;

    private Integer numberOfBedrooms;

    private String streetNumberAndName;

    private String addressComplements;

    private String zipcode;

    private String country;

    private String description;

    @TypeConverters({MediaConverter.class})
    private List<String> mediaPaths;

    private Double latitude;

    private Double longitude;

    @TypeConverters(EstateStatusConverter.class)
    private EstateStatus status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstateType getType() {
        return type;
    }

    public void setType(EstateType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getPriceInDollars() {
        return priceInDollars;
    }

    public void setPriceInDollars(Float priceInDollars) {
        this.priceInDollars = priceInDollars;
    }

    public Integer getSurfaceInMeterSquare() {
        return surfaceInMeterSquare;
    }

    public void setSurfaceInMeterSquare(Integer surfaceInMeterSquare) {
        this.surfaceInMeterSquare = surfaceInMeterSquare;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(Integer numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public Integer getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(Integer numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public String getStreetNumberAndName() {
        return streetNumberAndName;
    }

    public void setStreetNumberAndName(String streetNumberAndName) {
        this.streetNumberAndName = streetNumberAndName;
    }

    public String getAddressComplements() {
        return addressComplements;
    }

    public void setAddressComplements(String addressComplements) {
        this.addressComplements = addressComplements;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMediaPaths() {
        return mediaPaths;
    }

    public void setMediaPaths(List<String> mediaPaths) {
        this.mediaPaths = mediaPaths;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public EstateStatus getStatus() {
        return status;
    }

    public void setStatus(EstateStatus status) {
        this.status = status;
    }
}

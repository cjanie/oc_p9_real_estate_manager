package com.openclassrooms.realestatemanager.data.database.dtoentities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.data.database.typeconverters.EstateTypeConverter;

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
}

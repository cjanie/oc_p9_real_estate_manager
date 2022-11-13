package com.openclassrooms.realestatemanager.businesslogic.entities;


import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateStatus;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Estate {

    private Integer id;

    private EstateType type;

    private String location;

    private Float price;

    private Devise devise;

    private String streetNumberAndStreetName;

    private String addressComplements;

    private String zipCode;

    private String country;

    private String photoUrl;

    private Integer surface;

    private Integer numberOfRooms;

    private Integer numberOfBathrooms;

    private Integer numberOfBedrooms;

    private String description;

    private List<String> media;

    private Double latitude;

    private Double longitude;

    private EstateStatus status;

    private LocalDate dateOfEntreeIntoMarket;

    private LocalDate dateOfSale;

    private Agent agent;

    // Constructor

    public Estate() {
        this.media = new ArrayList<>();
    }

    // Getters and setters

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public String getStreetNumberAndStreetName() {
        return streetNumberAndStreetName;
    }

    public void setStreetNumberAndStreetName(String streetNumberAndStreetName) {
        this.streetNumberAndStreetName = streetNumberAndStreetName;
    }

    public String getAddressComplements() {
        return addressComplements;
    }

    public void setAddressComplements(String addressComplements) {
        this.addressComplements = addressComplements;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getSurface() {
        return surface;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
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

    public LocalDate getDateOfEntreeIntoMarket() {
        return dateOfEntreeIntoMarket;
    }

    public void setDateOfEntreeIntoMarket(LocalDate dateOfEntreeIntoMarket) {
        this.dateOfEntreeIntoMarket = dateOfEntreeIntoMarket;
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}

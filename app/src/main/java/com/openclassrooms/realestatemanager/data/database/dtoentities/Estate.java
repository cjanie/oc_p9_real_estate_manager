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
}

package com.openclassrooms.realestatemanager.data.database.typeconverters;

import androidx.room.TypeConverter;

import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;

public class EstateTypeConverter {

    @TypeConverter
    public String estateTypeToString(EstateType estateType) {
        return estateType.toString();
    }

    @TypeConverter
    public EstateType stringToEstateType(String string) {
        if(string.equals(EstateType.DUPLEX.toString()))
            return EstateType.DUPLEX;
        if(string.equals(EstateType.HOUSE.toString()))
            return EstateType.HOUSE;
        else return EstateType.FLAT;
    }

}

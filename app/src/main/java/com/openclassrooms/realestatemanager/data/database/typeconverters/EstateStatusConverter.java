package com.openclassrooms.realestatemanager.data.database.typeconverters;

import androidx.room.TypeConverter;

import com.openclassrooms.realestatemanager.businesslogic.enums.EstateStatus;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;

public class EstateStatusConverter {
    @TypeConverter
    public String estateStatusToJson(EstateStatus estateStatus) {
        return estateStatus.toString();
    }

    @TypeConverter
    public EstateStatus jsonToEstateStatus(String json) {
        if (json.equals(EstateStatus.SALE.toString())) {
            return EstateStatus.SALE;
        } else {
            return EstateStatus.SOLD;
        }
    }
}

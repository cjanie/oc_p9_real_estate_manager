package com.openclassrooms.realestatemanager.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.data.database.dtoentities.Estate;
import com.openclassrooms.realestatemanager.data.database.typeconverters.EstateTypeConverter;

import java.util.List;

@Dao
public interface EstateDAO {

    //@Query("SELECT id, type, location, priceInDollars, mediaPaths, latitude, longitude FROM Estate")
    @Query("SELECT id, type, location, priceInDollars, mediaList, status, latitude, longitude FROM Estate")
    List<Estate> findAll();

    @Query("SELECT id, type, location, priceInDollars, mediaList, status FROM Estate WHERE type= :type")
    List<Estate> searchByType(String type);

    @Query("SELECT id, type, location, priceInDollars, mediaList, status FROM Estate WHERE location= :location")
    List<Estate> searchByLocation(String location);

    @Query("SELECT id, type, location, priceInDollars, mediaList, status FROM Estate WHERE priceInDollars<= :maxPriceInDollars")
    List<Estate> searchUnderMaxPrice(Float maxPriceInDollars);

    @Query("SELECT * FROM Estate WHERE id= :id")
    Estate findEstateById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long save(Estate estate);
}

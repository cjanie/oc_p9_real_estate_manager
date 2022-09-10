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

    @Query("SELECT * FROM Estate")
    List<Estate> findAll();

    @Query("SELECT * FROM Estate WHERE type= :type")
    List<Estate> search(String type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long save(Estate estate);
}

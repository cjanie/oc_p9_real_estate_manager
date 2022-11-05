package com.openclassrooms.realestatemanager.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.openclassrooms.realestatemanager.data.database.dtoentities.EstateWithMedia;

import java.util.List;

@Dao
public interface EstateWithMediaDAO {

    @Transaction
    @Query("SELECT * FROM Estate")
    List<EstateWithMedia> findAll();

    @Transaction
    @Query("SELECT * FROM Estate WHERE id = :id")
    EstateWithMedia findById(long id);

}

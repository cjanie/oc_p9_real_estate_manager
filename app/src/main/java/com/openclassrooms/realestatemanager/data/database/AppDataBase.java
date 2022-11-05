package com.openclassrooms.realestatemanager.data.database;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.openclassrooms.realestatemanager.data.database.dao.EstateDAO;
import com.openclassrooms.realestatemanager.data.database.dao.EstateWithMediaDAO;
import com.openclassrooms.realestatemanager.data.database.dtoentities.Estate;
import com.openclassrooms.realestatemanager.data.database.dtoentities.EstateWithMedia;
import com.openclassrooms.realestatemanager.data.database.dtoentities.Media;
import com.openclassrooms.realestatemanager.data.database.typeconverters.MediaConverter;

@Database(entities = {Media.class, Estate.class}, version = 1)
@TypeConverters({MediaConverter.class})
public abstract class AppDataBase extends RoomDatabase {

    // Singleton
    private static volatile AppDataBase INSTANCE;

    // DAO
    public abstract EstateDAO estateDAO();
    public abstract EstateWithMediaDAO estateWithMediaDAO();

    // INSTANCE
    public static AppDataBase getInstance(Application application) {
        if(AppDataBase.INSTANCE == null) {
            AppDataBase.INSTANCE = Room.databaseBuilder(application.getApplicationContext(),
                    AppDataBase.class, "AppDataBase.db"
            )
                    .allowMainThreadQueries()
                    .build();
        }
        return AppDataBase.INSTANCE;
    }
}

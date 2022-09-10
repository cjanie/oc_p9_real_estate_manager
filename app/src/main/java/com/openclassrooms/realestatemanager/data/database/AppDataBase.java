package com.openclassrooms.realestatemanager.data.database;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.openclassrooms.realestatemanager.data.database.dao.EstateDAO;
import com.openclassrooms.realestatemanager.data.database.dtoentities.Estate;

@Database(entities = {Estate.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    // Singleton
    private static volatile AppDataBase INSTANCE;

    // DAO
    public abstract EstateDAO estateDAO();

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

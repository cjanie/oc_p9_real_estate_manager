package com.openclassrooms.realestatemanager.data.database.typeconverters;

import androidx.room.TypeConverter;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.data.database.dtoentities.Media;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class MediaConverter {

    @TypeConverter
    public List<Media> fromJson(String json) {
        if(json == null) {
            return Collections.emptyList();
        }
        Type type = new TypeToken<List<Media>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    @TypeConverter
    public String fromMedias(List<Media> medias) {
        Gson gson = new Gson();
        return gson.toJson(medias);
    }
}

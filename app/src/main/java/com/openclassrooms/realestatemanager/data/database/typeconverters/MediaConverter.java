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
    public List<Media> fromString(String json) {
        if(json == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Media>>() {}.getType();
        return new Gson().fromJson(json, listType);
    }

    @TypeConverter
    public String fromList(List<Media> mediaList) {
        Gson gson = new Gson();
        String json = gson.toJson(mediaList);
        return json;
    }
}

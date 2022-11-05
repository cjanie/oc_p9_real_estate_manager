package com.openclassrooms.realestatemanager.data.database.dtoentities;

import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class EstateWithMedia {

    @Embedded
    private Estate estate;

    @Relation(parentColumn = "id", entityColumn = "estateId")
    private List<Media> media;

    public EstateWithMedia() {
        this.media = new ArrayList<>();
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }
}

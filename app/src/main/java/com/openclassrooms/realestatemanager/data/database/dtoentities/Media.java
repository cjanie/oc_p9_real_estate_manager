package com.openclassrooms.realestatemanager.data.database.dtoentities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Estate.class, parentColumns = "id", childColumns = "estateId"))
public class Media {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private Integer estateId;

    private String path;

    public Media() {
    }

    @Ignore
    public Media(String path) {
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEstateId() {
        return estateId;
    }

    public void setEstateId(Integer estateId) {
        this.estateId = estateId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

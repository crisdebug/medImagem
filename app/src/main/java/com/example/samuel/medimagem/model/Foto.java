package com.example.samuel.medimagem.model;

import java.io.File;
import java.io.Serializable;

public class Foto implements Serializable {
    private String imagePath;
    private boolean beingUsed;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isBeingUsed() {
        return beingUsed;
    }

    public void setBeingUsed(boolean beingUsed) {
        this.beingUsed = beingUsed;
    }
}

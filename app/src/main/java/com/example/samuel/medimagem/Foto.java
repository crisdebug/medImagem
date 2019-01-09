package com.example.samuel.medimagem;

import java.io.File;
import java.io.Serializable;

public class Foto implements Serializable {
    private File imagePath;
    private boolean beingUsed;

    public File getImagePath() {
        return imagePath;
    }

    public void setImagePath(File imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isBeingUsed() {
        return beingUsed;
    }

    public void setBeingUsed(boolean beingUsed) {
        this.beingUsed = beingUsed;
    }
}

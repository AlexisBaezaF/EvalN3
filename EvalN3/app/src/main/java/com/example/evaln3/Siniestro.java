package com.example.evaln3;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;

public abstract class Siniestro implements Serializable {

    protected String Name;
    protected String Desc;
    protected String LAT;
    protected String LON;
    protected Bitmap photo;

    public Siniestro(){
        Name = "";
        Desc = "";
        LAT = "";
        LON = "";
    }

    public String getName() {
        return Name;
    }

    public String getDesc() {
        return Desc;
    }

    public String getLAT() {
        return LAT;
    }

    public String getLON() {
        return LON;
    }

    public Bitmap getPhoto() {
        return photo;
    }
}

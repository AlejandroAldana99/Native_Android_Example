package com.example.niteclub;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class PlaceItem {
    //Atributos
    private String p_Name;

    public PlaceItem(String p_Name) {
        this.p_Name = p_Name;
    }

    public String getP_Name() {
        return p_Name;
    }

    public void setP_Name(String p_Name) {
        this.p_Name = p_Name;
    }
}

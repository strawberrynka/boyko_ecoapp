package com.example.ecoapp.data.api.guides.dto;

import com.example.ecoapp.data.models.Guide;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GuidesListDTO {
    @SerializedName("item")
    ArrayList<Guide> guides;

    public GuidesListDTO(ArrayList<Guide> guides) {
        this.guides = guides;
    }

    public ArrayList<Guide> getGuides() {
        return guides;
    }

    public void setGuides(ArrayList<Guide> guides) {
        this.guides = guides;
    }
}

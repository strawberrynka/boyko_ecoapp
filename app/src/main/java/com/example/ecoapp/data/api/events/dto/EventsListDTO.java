package com.example.ecoapp.data.api.events.dto;

import com.example.ecoapp.data.models.EventCustom;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventsListDTO {
    @SerializedName("item")
    private ArrayList<EventCustom> item;

    public EventsListDTO(ArrayList<EventCustom> item) {
        this.item = item;
    }

    public ArrayList<EventCustom> getItem() {
        return item;
    }

    public void setItem(ArrayList<EventCustom> item) {
        this.item = item;
    }
}

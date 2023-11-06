package com.example.ecoapp.data.api.users.dto;

import com.google.gson.annotations.SerializedName;

public class HabitDTO {

    @SerializedName("title")
    private String title;

    @SerializedName("id")
    private String id;

    public HabitDTO(String id, String title) {
        this.id = id;
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

package com.example.ecoapp.data.models;

import com.google.gson.annotations.SerializedName;

public class Search {
    @SerializedName("image")
    private String image;

    @SerializedName("title")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    public Search(String image, String name, String id, String type) {
        this.image = image;
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
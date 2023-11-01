package com.example.ecoapp.data.models;

public class Coming {
    private String image;
    private String name;
    private String id;

    public Coming() {}

    public Coming(String image, String name, String id) {
        this.image = image;
        this.name = name;
        this.id = id;
    }

    public Coming(String image, String name) {
        this.image = image;
        this.name = name;
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
}

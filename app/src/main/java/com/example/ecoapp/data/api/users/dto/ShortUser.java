package com.example.ecoapp.data.api.users.dto;

import com.google.gson.annotations.SerializedName;

public class ShortUser {
    @SerializedName("id")
    private String id;

    @SerializedName("login")
    private String name;

    @SerializedName("email")
    private String email;

    public ShortUser(String name, String id, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

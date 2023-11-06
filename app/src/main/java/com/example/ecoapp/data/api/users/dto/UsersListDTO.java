package com.example.ecoapp.data.api.users.dto;

import com.example.ecoapp.data.models.User;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UsersListDTO {
    @SerializedName("item")
    private ArrayList<User> item;

    public UsersListDTO(ArrayList<User> item) {
        this.item = item;
    }

    public ArrayList<User> getItem() {
        return item;
    }

    public void setItem(ArrayList<User> item) {
        this.item = item;
    }
}

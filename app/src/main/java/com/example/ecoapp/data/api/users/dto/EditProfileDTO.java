package com.example.ecoapp.data.api.users.dto;

import com.google.gson.annotations.SerializedName;

public class EditProfileDTO {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("old_password")
    private String old_password;

    @SerializedName("new_password")
    private String new_password;

    public EditProfileDTO(String id, String name, String old_password, String new_password) {
        this.id = id;
        this.name = name;
        this.old_password = old_password;
        this.new_password = new_password;
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

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}

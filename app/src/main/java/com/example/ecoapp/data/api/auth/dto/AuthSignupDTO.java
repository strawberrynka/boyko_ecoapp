package com.example.ecoapp.data.api.auth.dto;

import com.google.gson.annotations.SerializedName;

public class AuthSignupDTO extends AuthLoginDTO {
    @SerializedName("name")
    private String name;

    public AuthSignupDTO(String name, String password, String email) {
        super(email, password);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

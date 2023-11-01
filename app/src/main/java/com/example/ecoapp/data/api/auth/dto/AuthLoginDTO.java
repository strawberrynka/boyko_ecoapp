package com.example.ecoapp.data.api.auth.dto;

import com.google.gson.annotations.SerializedName;

public class AuthLoginDTO {
    @SerializedName("password")
    protected String password;

    @SerializedName("email")
    protected String email;

    public AuthLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.example.ecoapp.data.api.auth.dto;

import com.example.ecoapp.data.api.users.dto.ShortUser;
import com.google.gson.annotations.SerializedName;

public class AuthResponseDTO {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private ShortUser shortUser;

    public AuthResponseDTO(String token, ShortUser shortUser) {
        this.token = token;
        this.shortUser = shortUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ShortUser getShortUser() {
        return shortUser;
    }

    public void setShortUser(ShortUser shortUser) {
        this.shortUser = shortUser;
    }
}

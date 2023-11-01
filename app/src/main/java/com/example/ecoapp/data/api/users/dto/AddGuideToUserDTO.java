package com.example.ecoapp.data.api.users.dto;

import com.google.gson.annotations.SerializedName;

public class AddGuideToUserDTO {
    @SerializedName("userID")
    private String userID;

    @SerializedName("guideID")
    private String guideID;

    public AddGuideToUserDTO() {}

    public AddGuideToUserDTO(String userID, String guideID) {
        this.userID = userID;
        this.guideID = guideID;
    }

    public void setGuideID(String guideID) {
        this.guideID = guideID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getGuideID() {
        return guideID;
    }

    public String getUserID() {
        return userID;
    }
}

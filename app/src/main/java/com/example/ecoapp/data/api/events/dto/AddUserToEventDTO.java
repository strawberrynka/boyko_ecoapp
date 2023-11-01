package com.example.ecoapp.data.api.events.dto;

import com.google.gson.annotations.SerializedName;

public class AddUserToEventDTO {
    @SerializedName("eventID")
    private String eventID;

    @SerializedName("authorID")
    private String authorID;

    public AddUserToEventDTO() {}

    public AddUserToEventDTO(String eventID, String authorID) {
        this.eventID = eventID;
        this.authorID = authorID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }
}

package com.example.ecoapp.data.api.users.dto;

import com.google.gson.annotations.SerializedName;

public class ChangeScoresDTO {
    @SerializedName("id")
    private String id;

    @SerializedName("eventID")
    private String eventID;

    @SerializedName("authorID")
    private String authorID;

    public ChangeScoresDTO(String id, String eventID, String authorID) {
        this.id = id;
        this.eventID = eventID;
        this.authorID = authorID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

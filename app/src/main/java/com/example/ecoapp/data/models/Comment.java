package com.example.ecoapp.data.models;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("photo")
    private String profileImage;

    @SerializedName("login")
    private String profileName;

    @SerializedName("content")
    private String content;

    @SerializedName("dateOfCreated")
    private String date;

    @SerializedName("id")
    private String id;

    @SerializedName("authorID")
    private String authorID;

    @SerializedName("eventID")
    private String eventID;

    public Comment() {}

    public Comment(String profileImage, String profileName, String content, String date, String id, String authorID, String eventID) {
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.content = content;
        this.date = date;
        this.id = id;
        this.authorID = authorID;
        this.eventID = eventID;
    }

    public Comment(String profileImage, String profileName, String content, String date, String id) {
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.content = content;
        this.date = date;
        this.id = id;
    }

    public Comment(String authorID, String eventID, String content) {
        this.eventID = eventID;
        this.authorID = authorID;
        this.content = content;
    }

    public Comment(String profileImage, String profileName, String content, String date) {
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.content = content;
        this.date = date;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}

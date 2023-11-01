package com.example.ecoapp.data.models;

import com.google.gson.annotations.SerializedName;

public class Guide {
    @SerializedName("title")
    private String title;

    @SerializedName("photo")
    private String photo;

    @SerializedName("description")
    private String description;

    @SerializedName("authorID")
    private String authorID;

    @SerializedName("source")
    private String source;

    @SerializedName("guideID")
    private String guideID;

    public Guide() {}

    public Guide(String title, String description, String source) {
        this.title = title;
        this.description = description;
        this.source = source;
    }

    public Guide(String title, String description, String authorID, String source) {
        this.title = title;
        this.description = description;
        this.authorID = authorID;
        this.source = source;
    }

    public Guide(String title, String photo, String description, String authorID, String source, String guideID) {
        this.title = title;
        this.photo = photo;
        this.description = description;
        this.authorID = authorID;
        this.source = source;
        this.guideID = guideID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGuideID() {
        return guideID;
    }

    public void setGuideID(String guideID) {
        this.guideID = guideID;
    }
}

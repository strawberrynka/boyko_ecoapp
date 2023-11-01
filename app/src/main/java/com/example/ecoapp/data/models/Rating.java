package com.example.ecoapp.data.models;

import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("authorID")
    private String authorID;

    @SerializedName("mark")
    private float mark;

    @SerializedName("ratingID")
    private String ratingID;

    @SerializedName("guideID")
    private String guideID;

    public Rating(String authorID, float mark, String guideID) {
        this.authorID = authorID;
        this.mark = mark;
        this.guideID = guideID;
    }

    public Rating(String authorID, float mark, String ratingID, String guideID) {
        this.authorID = authorID;
        this.mark = mark;
        this.ratingID = ratingID;
        this.guideID = guideID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public String getRatingID() {
        return ratingID;
    }

    public void setRatingID(String ratingID) {
        this.ratingID = ratingID;
    }

    public String getGuideID() {
        return guideID;
    }

    public void setGuideID(String guideID) {
        this.guideID = guideID;
    }
}

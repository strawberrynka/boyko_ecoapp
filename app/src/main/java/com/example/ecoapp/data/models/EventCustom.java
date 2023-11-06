package com.example.ecoapp.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventCustom {
    @SerializedName("title")
    private String title;

    @SerializedName("photo")
    private String photo;

    @SerializedName("description")
    private String description;

    @SerializedName("time")
    private String time;

    @SerializedName("place")
    private String place;

    @SerializedName("authorID")
    private String authorID;

    @SerializedName("scores")
    private Integer scores;

    @SerializedName("maxUsers")
    private Integer maxUsers;

    @SerializedName("currentUsers")
    private Integer currentUsers;

    @SerializedName("eventID")
    private String eventID;

    @SerializedName("usersList")
    private ArrayList<String> usersList;

    @SerializedName("lat")
    private double lat;

    @SerializedName("longt")
    private double longt;

    @SerializedName("dateOfCreated")
    private String date;

    @SerializedName("authorName")
    private String authorName;

    public EventCustom() {}

    public EventCustom(String title, String photo, String description, String time, String place, String authorID, Integer scores, Integer maxUsers, String authorName) {
        this.title = title;
        this.photo = photo;
        this.description = description;
        this.time = time;
        this.place = place;
        this.authorID = authorID;
        this.scores = scores;
        this.maxUsers = maxUsers;
        this.currentUsers = 1;
        this.authorName = authorName;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public Integer getScores() {
        return scores;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public Integer getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(Integer currentUsers) {
        this.currentUsers = currentUsers;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public ArrayList<String> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<String> usersList) {
        this.usersList = usersList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
        this.date = data;
    }

    public double getLat() {
        return lat;
    }

    public double getLongt() {
        return longt;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}

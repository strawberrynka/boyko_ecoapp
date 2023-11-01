package com.example.ecoapp.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Task {
    @SerializedName("title")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("scores")
    private Integer scores;

    @SerializedName("taskID")
    private String taskID;

    @SerializedName("authorID")
    private String authorID;

    @SerializedName("userID")
    private String userID;

    @SerializedName("dateOfCreated")
    private String dateOfCreated;

    @SerializedName("images")
    private ArrayList<String> images;

    @SerializedName("userDescription")
    private String userDescription;

    public Task(String name) {
        this.name = name;
    }

    public Task() {}

    public Task(String name, String description, Integer scores, String authorID) {
        this.name = name;
        this.description = description;
        this.scores = scores;
        this.authorID = authorID;
    }

    public Task(String name, String description, Integer scores, String taskID, String authorID, String userID, String dateOfCreated, ArrayList<String> images, String userDescription) {
        this.name = name;
        this.description = description;
        this.scores = scores;
        this.taskID = taskID;
        this.authorID = authorID;
        this.userID = userID;
        this.dateOfCreated = dateOfCreated;
        this.images = images;
        this.userDescription = userDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScores() {
        return scores;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(String dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }
}

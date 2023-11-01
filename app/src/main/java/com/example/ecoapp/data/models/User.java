package com.example.ecoapp.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {

    @SerializedName("photo")
    private String photo;

    @SerializedName("scores")
    private int scores;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("id")
    private String id;

    @SerializedName("login")
    private String login;

    @SerializedName("habitsList")
    private ArrayList<String> habitsList;

    @SerializedName("guidesList")
    private ArrayList<String> guidesList;

    public User() {}

    public User(String name, String email, String password, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public User(String image, String name) {
        this.photo = image;
        this.name = name;
    }

    public User(String login) {
        this.login = login;
    }

    public User(String photo, int scores, String name, String email, String password, String id, String login, ArrayList<String> habitsList, ArrayList<String> guidesList) {
        this.photo = photo;
        this.scores = scores;
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.login = login;
        this.habitsList = habitsList;
        this.guidesList = guidesList;
    }

    public String getImage() {
        return photo;
    }

    public void setImage(String image) {
        this.photo = image;
    }

    public int getScores() { return scores; }

    public void setScores(int scores) { this.scores = scores; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getHabitsList() {
        return habitsList;
    }

    public void setHabitsList(ArrayList<String> habitsList) {
        this.habitsList = habitsList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getGuidesList() {
        return guidesList;
    }

    public void setGuidesList(ArrayList<String> guidesList) {
        this.guidesList = guidesList;
    }

    public String getLogin() {
        return login;
    }

    public String getPhoto() {
        return photo;
    }
}

package com.example.ecoapp.data.models;

import com.google.gson.annotations.SerializedName;

public class Habit {
    @SerializedName("title")
    private String title;

    @SerializedName("frequency")
    private int frequency;

    @SerializedName("type")
    private String type;

    @SerializedName("habitID")
    private String habitID;

    @SerializedName("authorID")
    private String authorID;

    @SerializedName("isDone")
    private boolean isDone;

    public Habit() {}

    public Habit(String title, int frequency, String type, String authorID) {
        this.title = title;
        this.frequency = frequency;
        this.type = type;
        this.authorID = authorID;
    }

    public Habit(String title, int frequency, String type, String habitID, String authorID, boolean isDone) {
        this.title = title;
        this.frequency = frequency;
        this.type = type;
        this.habitID = habitID;
        this.authorID = authorID;
        this.isDone = isDone;
    }

    public String getType() {
        return type;
    }

    public String getAuthorID() {
        return authorID;
    }

    public int getFrequency() {
        return frequency;
    }

    public String getHabitID() {
        return habitID;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setHabitID(String habitID) {
        this.habitID = habitID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDone() {
        return isDone;
    }
}


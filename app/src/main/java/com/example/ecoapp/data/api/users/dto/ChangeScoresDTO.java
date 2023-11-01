package com.example.ecoapp.data.api.users.dto;

import com.google.gson.annotations.SerializedName;

public class ChangeScoresDTO {
    @SerializedName("id")
    private String id;

    @SerializedName("scores")
    private Integer scores;

    @SerializedName("authorID")
    private String authorID;

    public ChangeScoresDTO(String id, Integer scores, String authorID) {
        this.id = id;
        this.scores = scores;
        this.authorID = authorID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getScores() {
        return scores;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }
}

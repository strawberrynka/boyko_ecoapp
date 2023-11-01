package com.example.ecoapp.data.api.events.dto;

import com.example.ecoapp.data.models.Comment;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommentsDTO {
    @SerializedName("item")
    private ArrayList<Comment> item;

    public CommentsDTO() {}

    public CommentsDTO(ArrayList<Comment> item) {
        this.item = item;
    }

    public ArrayList<Comment> getItem() {
        return item;
    }

    public void setItem(ArrayList<Comment> item) {
        this.item = item;
    }
}

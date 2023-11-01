package com.example.ecoapp.data.api.events.dto;

import com.example.ecoapp.data.models.Search;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchDTO {
    @SerializedName("item")
    private ArrayList<Search> searches;

    public SearchDTO() {}

    public SearchDTO(ArrayList<Search> searches) {
        this.searches = searches;
    }

    public ArrayList<Search> getSearches() {
        return searches;
    }

    public void setSearches(ArrayList<Search> searches) {
        this.searches = searches;
    }
}

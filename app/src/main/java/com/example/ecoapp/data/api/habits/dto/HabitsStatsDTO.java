package com.example.ecoapp.data.api.habits.dto;

import com.example.ecoapp.data.models.HabitStats;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HabitsStatsDTO {

    @SerializedName("item")
    private ArrayList<HabitStats> item;

    public HabitsStatsDTO() {}

    public HabitsStatsDTO(ArrayList<HabitStats> item) {
        this.item = item;
    }

    public ArrayList<HabitStats> getItem() {
        return item;
    }

    public void setItem(ArrayList<HabitStats> item) {
        this.item = item;
    }
}

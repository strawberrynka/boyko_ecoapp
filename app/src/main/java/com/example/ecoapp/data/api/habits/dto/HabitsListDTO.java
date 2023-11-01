package com.example.ecoapp.data.api.habits.dto;

import com.example.ecoapp.data.models.Habit;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HabitsListDTO {

    @SerializedName("item")
    private ArrayList<Habit> habitsList;

    public HabitsListDTO() {}

    public void setHabitsList(ArrayList<Habit> habitsList) {
        this.habitsList = habitsList;
    }

    public HabitsListDTO(ArrayList<Habit> habitsList) {
        this.habitsList = habitsList;
    }

    public ArrayList<Habit> getHabitsList() {
        return habitsList;
    }
}

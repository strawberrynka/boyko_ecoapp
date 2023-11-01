package com.example.ecoapp.data.api.habits.dto;

import com.google.gson.annotations.SerializedName;

public class UpdateHabitDTO {
    @SerializedName("habitID")
    private String habitID;

    public UpdateHabitDTO() {}

    public UpdateHabitDTO(String habitID) {
        this.habitID = habitID;
    }

    public String getHabitID() {
        return habitID;
    }

    public void setHabitID(String habitID) {
        this.habitID = habitID;
    }
}

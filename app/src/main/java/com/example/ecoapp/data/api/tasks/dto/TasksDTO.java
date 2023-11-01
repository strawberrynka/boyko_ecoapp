package com.example.ecoapp.data.api.tasks.dto;

import com.example.ecoapp.data.models.Task;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TasksDTO {

    @SerializedName("item")
    private ArrayList<Task> tasks;

    public TasksDTO() {}

    public TasksDTO(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}

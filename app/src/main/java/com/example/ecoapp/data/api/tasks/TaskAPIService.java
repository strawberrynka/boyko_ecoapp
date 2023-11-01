package com.example.ecoapp.data.api.tasks;

import com.example.ecoapp.data.api.RetrofitService;

public class TaskAPIService {
    private final TaskAPI taskAPI;

    public TaskAPIService(RetrofitService retrofitService) {
        taskAPI = retrofitService.getInstance().create(TaskAPI.class);
    }

    public TaskAPI getTaskAPI() {
        return taskAPI;
    }
}

package com.example.ecoapp.data.api.habits;

import com.example.ecoapp.data.api.RetrofitService;

public class HabitAPIService {
    private final HabitAPI habitAPI;

    public HabitAPIService(RetrofitService retrofitService) {
        habitAPI = retrofitService.getInstance().create(HabitAPI.class);
    }

    public HabitAPI getHabitAPI() {
        return habitAPI;
    }
}

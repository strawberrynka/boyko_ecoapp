package com.example.ecoapp.data.api.events;

import com.example.ecoapp.data.api.RetrofitService;

public class EventAPIService {
    private final EventAPI eventAPI;

    public EventAPIService(RetrofitService retrofitService) {
        eventAPI = retrofitService.getInstance().create(EventAPI.class);
    }

    public EventAPI getEventAPI() {
        return eventAPI;
    }
}

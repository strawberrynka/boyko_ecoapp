package com.example.ecoapp.data.api.users;

import com.example.ecoapp.data.api.RetrofitService;

public class UserAPIService {
    private final UserAPI userAPI;

    public UserAPIService(RetrofitService retrofitService) {
        userAPI = retrofitService.getInstance().create(UserAPI.class);
    }

    public UserAPI getUserAPI() {
        return userAPI;
    }
}

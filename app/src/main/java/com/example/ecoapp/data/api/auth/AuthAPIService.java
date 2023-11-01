package com.example.ecoapp.data.api.auth;

import com.example.ecoapp.data.api.RetrofitService;

public class AuthAPIService {
    private final AuthAPI authAPI;

    public AuthAPIService(RetrofitService retrofitService) {
        authAPI = retrofitService.getInstance().create(AuthAPI.class);
    }

    public AuthAPI getAuthAPI() {
        return authAPI;
    }
}

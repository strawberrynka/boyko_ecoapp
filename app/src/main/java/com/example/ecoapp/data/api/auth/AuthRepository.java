package com.example.ecoapp.data.api.auth;

import com.example.ecoapp.data.api.RetrofitService;
import com.example.ecoapp.data.api.auth.dto.AuthLoginDTO;
import com.example.ecoapp.data.api.auth.dto.AuthResponseDTO;
import com.example.ecoapp.data.api.auth.dto.AuthSignupDTO;

import retrofit2.Call;

public class AuthRepository {
    private final AuthAPI authAPI;

    public AuthRepository() {
        authAPI = new AuthAPIService(new RetrofitService()).getAuthAPI();
    }

    public Call<AuthResponseDTO> login(String email, String password) {
        return authAPI.login(new AuthLoginDTO(email, password));
    }

    public Call<AuthResponseDTO> signup(String name, String password, String email) {
        return authAPI.signup(new AuthSignupDTO(name, password, email));
    }
}

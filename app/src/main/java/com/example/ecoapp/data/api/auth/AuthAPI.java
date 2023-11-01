package com.example.ecoapp.data.api.auth;

import com.example.ecoapp.data.api.auth.dto.AuthLoginDTO;
import com.example.ecoapp.data.api.auth.dto.AuthResponseDTO;
import com.example.ecoapp.data.api.auth.dto.AuthSignupDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("/auth/users/login")
    Call<AuthResponseDTO> login(@Body AuthLoginDTO authLoginDTO);

    @POST("/auth/users/signup")
    Call<AuthResponseDTO> signup(@Body AuthSignupDTO authSignupDTO);
}
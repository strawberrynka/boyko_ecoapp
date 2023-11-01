package com.example.ecoapp.presentation.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecoapp.data.api.auth.AuthRepository;
import com.example.ecoapp.data.api.auth.dto.AuthResponseDTO;
import com.example.ecoapp.domain.helpers.StorageHandler;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends AndroidViewModel {
    private final MutableLiveData<AuthResponseDTO> userData = new MutableLiveData<>();
    private final MutableLiveData<Integer> statusCode = new MutableLiveData<>(0);
    private final AuthRepository authRepository;
    private final StorageHandler storageHandler;
    private final MutableLiveData<Boolean> isNavigation = new MutableLiveData<>(false);

    public AuthViewModel(@NonNull Application application) {
        super(application);
        this.authRepository = new AuthRepository();
        storageHandler = new StorageHandler(application.getApplicationContext());
    }

    public LiveData<Integer> signup(String name, String password, String email) {

        authRepository.signup(name, password, email).enqueue(new Callback<AuthResponseDTO>() {
            @Override
            public void onResponse(@NotNull Call<AuthResponseDTO> call, @NotNull Response<AuthResponseDTO> response) {
                statusCode.setValue(response.code());
                if (response.isSuccessful() && response.body() != null) {
                    userData.setValue(response.body());
                    storageHandler.setToken(response.body().getToken());
                    storageHandler.saveUserID(response.body().getShortUser().getId());
                    isNavigation.setValue(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<AuthResponseDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
                userData.setValue(null);
                statusCode.setValue(400);
            }
        });

        return statusCode;
    }

    public LiveData<Integer> login(String email, String password) {
        authRepository.login(email, password).enqueue(new Callback<AuthResponseDTO>() {
            @Override
            public void onResponse(@NotNull Call<AuthResponseDTO> call, @NotNull Response<AuthResponseDTO> response) {
                statusCode.setValue(response.code());
                if (response.isSuccessful() && response.body() != null) {
                    userData.setValue(response.body());
                    storageHandler.setToken(response.body().getToken());
                    storageHandler.saveUserID(response.body().getShortUser().getId());
                    isNavigation.setValue(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<AuthResponseDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
                userData.setValue(null);
                statusCode.setValue(400);
            }
        });

        return statusCode;
    }

    public LiveData<Boolean> getNavigate() {
        return isNavigation;
    }

    public void cancelNavigate() {
        isNavigation.setValue(false);
    }
}

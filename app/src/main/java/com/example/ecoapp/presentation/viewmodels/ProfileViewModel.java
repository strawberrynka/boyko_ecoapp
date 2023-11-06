package com.example.ecoapp.presentation.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecoapp.data.api.RetrofitService;
import com.example.ecoapp.data.api.users.UserAPIService;
import com.example.ecoapp.data.api.users.UserRepository;
import com.example.ecoapp.data.models.User;
import com.example.ecoapp.domain.helpers.StorageHandler;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> statusCode = new MutableLiveData<>(0);
    private UserRepository userRepository;
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<File> userImage = new MutableLiveData<>();
    private StorageHandler storageHandler;

    public ProfileViewModel(@NotNull Application application) {
        super(application);

        storageHandler = new StorageHandler(application);
        userRepository = new UserRepository(new UserAPIService(new RetrofitService()));
    }

    public LiveData<Integer> savePhoto(String token, File photo, String userID) {
        userRepository.addPhoto(token, userID, photo).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                statusCode.setValue(response.code());
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                t.printStackTrace();
                statusCode.setValue(400);
            }
        });

        return statusCode;
    }

    public LiveData<User> getUserData(String token, String userID) {
        userRepository.getUser(storageHandler.getToken(), storageHandler.getUserID()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                statusCode.setValue(response.code());
                if (response.isSuccessful() && response.body() != null) {
                    user.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                t.printStackTrace();
                statusCode.setValue(400);
            }
        });

        return user;
    }

    public LiveData<User> updateGuideToUser(String guideID) {
        userRepository.updateGuideToUser(storageHandler.getToken(), storageHandler.getUserID(), guideID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if (response.isSuccessful() && response.body() != null) user.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return user;
    }

    public LiveData<Integer> updateUserScores(String userID, String eventID) {
        statusCode.setValue(0);
        userRepository.changeScores(storageHandler.getToken(), storageHandler.getUserID(), userID, eventID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                statusCode.setValue(response.code());
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return statusCode;
    }

    public LiveData<Integer> updateName(String name) {
        statusCode.setValue(0);
        userRepository.editProfile(storageHandler.getToken(), storageHandler.getUserID(), name, "", "").enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                statusCode.setValue(response.code());
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return statusCode;
    }

    public LiveData<Integer> editLogin(String login) {
        statusCode.setValue(0);
        userRepository.updateLogin(storageHandler.getToken(), login).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                statusCode.setValue(response.code());
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return statusCode;
    }
}

package com.example.ecoapp.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecoapp.data.api.RetrofitService;
import com.example.ecoapp.data.api.guides.GuideAPIService;
import com.example.ecoapp.data.api.guides.GuideRepository;
import com.example.ecoapp.data.api.guides.dto.GuidesListDTO;
import com.example.ecoapp.data.models.Guide;
import com.example.ecoapp.data.models.Rating;
import com.example.ecoapp.domain.helpers.StorageHandler;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuideViewModel extends AndroidViewModel {
    private final GuideRepository guideRepository;
    private final StorageHandler storageHandler;
    private final MutableLiveData<ArrayList<Guide>> guidesList = new MutableLiveData<>();
    private final MutableLiveData<Integer> statusCode = new MutableLiveData<>(0);
    private final MutableLiveData<Guide> guide = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isNavigation = new MutableLiveData<>(false);
    private final MutableLiveData<Rating> rating = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Guide>> savedGuidesList = new MutableLiveData<>();

    public GuideViewModel(@NonNull Application application) {
        super(application);

        storageHandler = new StorageHandler(application);
        guideRepository = new GuideRepository(new GuideAPIService(new RetrofitService()));
    }

    public LiveData<Integer> createGuide(String title, String description, String source, File file) {
        statusCode.setValue(0);
        guideRepository.createGuide(storageHandler.getToken(), title, description, storageHandler.getUserID(), source, file).enqueue(new Callback<Guide>() {
            @Override
            public void onResponse(@NotNull Call<Guide> call, @NotNull Response<Guide> response) {
                statusCode.setValue(response.code());
                if (response.isSuccessful()) isNavigation.setValue(true);
            }

            @Override
            public void onFailure(@NotNull Call<Guide> call, @NotNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return statusCode;
    }

    public LiveData<Guide> getGuideByID(String guideID) {
        guideRepository.getGuideByID(storageHandler.getToken(), guideID).enqueue(new Callback<Guide>() {
            @Override
            public void onResponse(@NotNull Call<Guide> call, @NotNull Response<Guide> response) {
                if (response.isSuccessful() && response.body() != null) {
                    guide.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Guide> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return guide;
    }

    public LiveData<ArrayList<Guide>> getGuidesList() {
        guideRepository.getGuidesList(storageHandler.getToken()).enqueue(new Callback<ArrayList<Guide>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<Guide>> call, @NotNull Response<ArrayList<Guide>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    guidesList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ArrayList<Guide>> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return guidesList;
    }

    public LiveData<Integer> updateGuide(String title, String description, String source, File photo) {
        statusCode.setValue(0);
        guideRepository.changeGuide(storageHandler.getToken(), title, description, source, photo).enqueue(new Callback<ResponseBody>() {
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

    public LiveData<Integer> deleteGuide(String guideID) {
        statusCode.setValue(0);
        guideRepository.deleteGuide(storageHandler.getToken(), guideID).enqueue(new Callback<ResponseBody>() {
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

    public LiveData<Boolean> getNavigation() {
        return isNavigation;
    }

    public void setCancelNavigate() {
        isNavigation.setValue(false);
    }

    public LiveData<Rating> getRating(String guideID) {
        guideRepository.getRating(storageHandler.getToken(), guideID, storageHandler.getUserID()).enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(@NotNull Call<Rating> call, @NotNull Response<Rating> response) {
                if (response.isSuccessful() && response.body() != null) {
                    rating.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Rating> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return rating;
    }

    public LiveData<Integer> setRating(String guideID, float mark) {
        statusCode.setValue(0);
        guideRepository.setRating(storageHandler.getToken(), guideID, storageHandler.getUserID(), mark).enqueue(new Callback<ResponseBody>() {
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

    public LiveData<ArrayList<Guide>> getGuidesSavedList() {
        guideRepository.getGuidesSavedList(storageHandler.getToken(), storageHandler.getUserID()).enqueue(new Callback<GuidesListDTO>() {
            @Override
            public void onResponse(@NotNull Call<GuidesListDTO> call, @NotNull Response<GuidesListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    savedGuidesList.setValue(response.body().getGuides());
                }
            }

            @Override
            public void onFailure(@NotNull Call<GuidesListDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return savedGuidesList;
    }
}
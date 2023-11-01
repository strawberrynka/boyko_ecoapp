package com.example.ecoapp.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecoapp.data.api.RetrofitService;
import com.example.ecoapp.data.api.habits.HabitAPIService;
import com.example.ecoapp.data.api.habits.HabitRepository;
import com.example.ecoapp.data.api.habits.dto.HabitsListDTO;
import com.example.ecoapp.data.api.habits.dto.HabitsStatsDTO;
import com.example.ecoapp.data.models.Habit;
import com.example.ecoapp.data.models.HabitStats;
import com.example.ecoapp.domain.helpers.StorageHandler;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitViewModel extends AndroidViewModel {
    private StorageHandler storageHandler;
    private HabitRepository habitRepository;
    private MutableLiveData<ArrayList<Habit>> habitsList = new MutableLiveData<>();
    private MutableLiveData<Integer> statusCode = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNavigation = new MutableLiveData<>(false);
    private MutableLiveData<ArrayList<HabitStats>> habitStats = new MutableLiveData<>();

    public HabitViewModel(@NonNull Application application) {
        super(application);

        storageHandler = new StorageHandler(application);
        habitRepository = new HabitRepository(new HabitAPIService(new RetrofitService()));
    }

    public LiveData<Integer> createHabit(String title, String type) {
        statusCode.setValue(0);
        habitRepository.createHabit(storageHandler.getToken(), title, 0, type, storageHandler.getUserID()).enqueue(new Callback<Habit>() {
            @Override
            public void onResponse(@NotNull Call<Habit> call, @NotNull Response<Habit> response) {
                statusCode.setValue(response.code());
                isNavigation.setValue(true);
            }

            @Override
            public void onFailure(@NotNull Call<Habit> call, @NotNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return statusCode;
    }

    public LiveData<ArrayList<Habit>> getHabitsList() {
        habitRepository.getListHabits(storageHandler.getToken(), storageHandler.getUserID()).enqueue(new Callback<HabitsListDTO>() {
            @Override
            public void onResponse(@NotNull Call<HabitsListDTO> call, @NotNull Response<HabitsListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    habitsList.setValue(response.body().getHabitsList());
                }
            }

            @Override
            public void onFailure(@NotNull Call<HabitsListDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return habitsList;
    }

    public LiveData<ArrayList<Habit>> getHabitsListByType(String type) {
        habitRepository.getHabitsByType(storageHandler.getToken(), storageHandler.getUserID(), type).enqueue(new Callback<HabitsListDTO>() {
            @Override
            public void onResponse(@NotNull Call<HabitsListDTO> call, @NotNull Response<HabitsListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    habitsList.setValue(response.body().getHabitsList());
                }
            }

            @Override
            public void onFailure(@NotNull Call<HabitsListDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return habitsList;
    }

    public LiveData<Integer> makeHabitDone(String habitID) {
        statusCode.setValue(0);

        habitRepository.habitUpdate(storageHandler.getToken(), habitID).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                statusCode.setValue(response.code());
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                t.printStackTrace();
                statusCode.setValue(400);
            }
        });

        return statusCode;
    }

    public LiveData<ArrayList<HabitStats>> getHabitStats() {
        habitRepository.getStatistics(storageHandler.getToken(), storageHandler.getUserID(), "daily").enqueue(new Callback<HabitsStatsDTO>() {
            @Override
            public void onResponse(@NotNull Call<HabitsStatsDTO> call, @NotNull Response<HabitsStatsDTO> response) {
                if (response.isSuccessful() && response.body() != null) habitStats.setValue(response.body().getItem());
            }

            @Override
            public void onFailure(@NotNull Call<HabitsStatsDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return habitStats;
    }

    public LiveData<Boolean> isNavigate() {
        return isNavigation;
    }

    public void cancelNavigate() {
        isNavigation.setValue(false);
    }

    public void clearData() {
        habitsList.setValue(null);
    }
}

package com.example.ecoapp.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecoapp.data.api.RetrofitService;
import com.example.ecoapp.data.api.tasks.TaskAPIService;
import com.example.ecoapp.data.api.tasks.TaskRepository;
import com.example.ecoapp.data.api.tasks.dto.TasksDTO;
import com.example.ecoapp.data.models.Task;
import com.example.ecoapp.data.models.User;
import com.example.ecoapp.domain.helpers.StorageHandler;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskViewModel extends AndroidViewModel {
    private StorageHandler storageHandler;
    private TaskRepository taskRepository;
    private MutableLiveData<ArrayList<Task>> tasksList = new MutableLiveData<>();
    private MutableLiveData<Task> taskData = new MutableLiveData<>();
    private MutableLiveData<Integer> statusCode = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNavigation = new MutableLiveData<>(false);
    private MutableLiveData<ArrayList<Task>> tasksExecuteList = new MutableLiveData<>();

    public TaskViewModel(@NonNull Application application) {
        super(application);

        storageHandler = new StorageHandler(application);
        taskRepository = new TaskRepository(new TaskAPIService(new RetrofitService()));
    }

    public LiveData<Integer> createTask(String title, String description, int scores) {
        statusCode.setValue(0);
        taskRepository.createTask(storageHandler.getToken(), title, scores, storageHandler.getUserID(), description).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                statusCode.setValue(response.code());
                if (response.isSuccessful()) isNavigation.setValue(true);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return statusCode;
    }

    public LiveData<ArrayList<Task>> getTasksList() {
        isNavigation.setValue(false);
        taskRepository.getTasksList(storageHandler.getToken(), storageHandler.getUserID()).enqueue(new Callback<TasksDTO>() {
            @Override
            public void onResponse(@NotNull Call<TasksDTO> call, @NotNull Response<TasksDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tasksList.setValue(response.body().getTasks());
                    isNavigation.setValue(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<TasksDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return tasksList;
    }

    public LiveData<ArrayList<Task>> getTasksListWithUser() {
        isNavigation.setValue(false);
        taskRepository.getTasksListWithUser(storageHandler.getToken(), storageHandler.getUserID()).enqueue(new Callback<TasksDTO>() {
            @Override
            public void onResponse(@NotNull Call<TasksDTO> call, @NotNull Response<TasksDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tasksExecuteList.setValue(response.body().getTasks());
                    isNavigation.setValue(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<TasksDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return tasksExecuteList;
    }

    public LiveData<Task> getTask(String taskID) {
        taskRepository.getTaskByID(storageHandler.getToken(), taskID).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(@NotNull Call<Task> call, @NotNull Response<Task> response) {
                if (response.isSuccessful() && response.body() != null) {
                    taskData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Task> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return taskData;
    }

    public LiveData<Integer> makeTaskDone(String taskID) {
        statusCode.setValue(0);
        taskRepository.makeTaskDone(storageHandler.getToken(), taskID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                statusCode.setValue(response.code());
                isNavigation.setValue(true);
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return statusCode;
    }

    public LiveData<Integer> deleteTask(String taskID) {
        statusCode.setValue(0);
        taskRepository.deleteTask(storageHandler.getToken(), taskID).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                statusCode.setValue(response.code());
                isNavigation.setValue(true);
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return statusCode;
    }

    public LiveData<ArrayList<Task>> getAllTasks() {
        taskRepository.getAllTasks(storageHandler.getToken()).enqueue(new Callback<TasksDTO>() {
            @Override
            public void onResponse(@NotNull Call<TasksDTO> call, @NotNull Response<TasksDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tasksList.setValue(response.body().getTasks());
                    isNavigation.setValue(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<TasksDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return tasksList;
    }

    public LiveData<Integer> takeTask(String taskID, String userDescription, File file1, File file2, File file3) {
        statusCode.setValue(0);
        taskRepository.takeTask(storageHandler.getToken(), taskID, storageHandler.getUserID(), userDescription, file1, file2, file3).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                statusCode.setValue(response.code());
                if (response.isSuccessful() && file1 != null) isNavigation.setValue(true);
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return statusCode;
    }

    public LiveData<Integer> cancelTakeTask(String taskID) {
        statusCode.setValue(0);
        taskRepository.cancelTakeTask(storageHandler.getToken(), taskID).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(@NotNull Call<Task> call, @NotNull Response<Task> response) {
                statusCode.setValue(response.code());
            }

            @Override
            public void onFailure(@NotNull Call<Task> call, @NotNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return statusCode;
    }

    public LiveData<Boolean> getNavigation() {
        return isNavigation;
    }

    public void setCancelNavigation() {
        isNavigation.setValue(false);
    }
}

package com.example.ecoapp.data.api.tasks;

import com.example.ecoapp.data.api.tasks.dto.TasksDTO;
import com.example.ecoapp.data.models.Task;
import com.example.ecoapp.data.models.User;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class TaskRepository {
    private final TaskAPI taskAPI;

    public TaskRepository(TaskAPIService taskAPIService) {
        taskAPI = taskAPIService.getTaskAPI();
    }

    public Call<ResponseBody> takeTask(String token, String taskID, String userID, String userDescription, File photo, File photo2, File photo3) {

        RequestBody taskIdBody = RequestBody.create(MediaType.parse("text/plain"), taskID);
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), userID);

        if (photo == null) return taskAPI.takeTask2(token, taskIdBody, userIdBody);

        RequestBody userDescriptionBody = RequestBody.create(MediaType.parse("text/plain"), userDescription);

        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), photo);
        MultipartBody.Part file = MultipartBody.Part.createFormData("img", photo.getName(), fileReqBody);

        RequestBody fileReqBody2 = RequestBody.create(MediaType.parse("image/*"), photo2);
        MultipartBody.Part file2 = MultipartBody.Part.createFormData("img", photo.getName(), fileReqBody2);

        RequestBody fileReqBody3 = RequestBody.create(MediaType.parse("image/*"), photo3);
        MultipartBody.Part file3 = MultipartBody.Part.createFormData("img", photo.getName(), fileReqBody3);

        return taskAPI.takeTask(token, taskIdBody, userIdBody, userDescriptionBody, file, file2, file3);
    }

    public Call<User> makeTaskDone(String token, String taskID) {
        return taskAPI.makeTaskDone(token, taskID);
    }

    public Call<TasksDTO> getTasksList(String token, String authorID) {
        return taskAPI.getTasksList(token, authorID);
    }

    public Call<TasksDTO> getTasksListWithUser(String token, String userID) {
        return taskAPI.getTasksListWithUser(token, userID);
    }


    public Call<ResponseBody> createTask(String token, String title, int scores, String authorID, String description) {
        return taskAPI.create(token, new Task(title, description, scores, authorID));
    }

    public Call<Task> getTaskByID(String token, String taskID) {
        return taskAPI.getTaskByID(token, taskID);
    }

    public Call<ResponseBody> deleteTask(String token, String taskID) {
        return taskAPI.deleteTask(token, taskID);
    }

    public Call<TasksDTO> getAllTasks(String token) {
        return taskAPI.getAllTasks(token);
    }

    public Call<Task> cancelTakeTask(String token, String taskID) {
        return taskAPI.cancelTakeTask(token, taskID);
    }
}

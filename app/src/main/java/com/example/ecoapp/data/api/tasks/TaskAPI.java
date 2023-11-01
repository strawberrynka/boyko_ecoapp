package com.example.ecoapp.data.api.tasks;

import com.example.ecoapp.data.api.tasks.dto.TasksDTO;
import com.example.ecoapp.data.models.Task;
import com.example.ecoapp.data.models.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface TaskAPI {

    @GET("/tasks/getTaskByID")
    Call<Task> getTaskByID(@Header("Authorization") String token, @Query("id") String id);

    @POST("/tasks/create")
    Call<ResponseBody> create(@Header("Authorization") String token, @Body Task taskDTO);

    @GET("/tasks/getTasksList")
    Call<TasksDTO> getTasksList(@Header("Authorization") String token, @Query("authorID") String authorID);

    @GET("/tasks/getTasksListWithUser")
    Call<TasksDTO> getTasksListWithUser(@Header("Authorization") String token, @Query("userID") String userID);

    @DELETE("/tasks/delete")
    Call<User> makeTaskDone(@Header("Authorization") String token, @Query("taskID") String taskID);

    @DELETE("/tasks/deleteTask")
    Call<ResponseBody> deleteTask(@Header("Authorization") String token, @Query("taskID") String taskID);

    @Multipart
    @POST("/tasks/takeTask")
    Call<ResponseBody> takeTask(@Header("Authorization") String token, @Part("taskID") RequestBody taskID, @Part("userID") RequestBody userID, @Part("userDescription") RequestBody userDescription, @Part MultipartBody.Part img1, @Part MultipartBody.Part img2, @Part MultipartBody.Part img3);

    @Multipart
    @POST("/tasks/takeTask")
    Call<ResponseBody> takeTask2(@Header("Authorization") String token, @Part("taskID") RequestBody taskID, @Part("userID") RequestBody userID);


    @GET("/tasks/getAllTasks")
    Call<TasksDTO> getAllTasks(@Header("Authorization") String token);

    @DELETE("/tasks/cancelTakeTask")
    Call<Task> cancelTakeTask(@Header("Authorization") String token, @Query("taskID") String taskID);
}

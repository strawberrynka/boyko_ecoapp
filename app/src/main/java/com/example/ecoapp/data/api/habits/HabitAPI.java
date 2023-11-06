package com.example.ecoapp.data.api.habits;

import com.example.ecoapp.data.api.habits.dto.HabitsListDTO;
import com.example.ecoapp.data.api.habits.dto.HabitsStatsDTO;
import com.example.ecoapp.data.api.habits.dto.UpdateHabitDTO;
import com.example.ecoapp.data.models.Habit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface HabitAPI {
    @POST("/habits/create")
    Call<Habit> createHabit(@Header("Authorization") String token, @Body Habit habitDTO);

    @DELETE("/habits/delete")
    Call<ResponseBody> deleteHabit(@Header("Authorization") String token, @Query("id") String id);

    @GET("/habits/getList")
    Call<HabitsListDTO> getListHabits(@Header("Authorization") String token, @Query("authorID") String authorID);

    @GET("/habits/getHabitsByType")
    Call<HabitsListDTO> getHabitsByType(@Header("Authorization") String token, @Query("authorID") String authorID, @Query("type") String type);

    @PUT("/habits/habitUpdate")
    Call<ResponseBody> habitUpdate(@Header("Authorization") String token, @Body UpdateHabitDTO id);

    @GET("/habits/getStatistics")
    Call<HabitsStatsDTO> getStatistics(@Header("Authorization") String token, @Query("authorID") String authorID, @Query("type") String type);

}

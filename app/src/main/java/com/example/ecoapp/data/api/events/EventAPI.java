package com.example.ecoapp.data.api.events;

import com.example.ecoapp.data.api.events.dto.AddUserToEventDTO;
import com.example.ecoapp.data.api.events.dto.CommentsDTO;
import com.example.ecoapp.data.api.events.dto.EventsListDTO;
import com.example.ecoapp.data.api.events.dto.SearchDTO;
import com.example.ecoapp.data.api.users.dto.UsersListDTO;
import com.example.ecoapp.data.models.Comment;
import com.example.ecoapp.data.models.EventCustom;

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
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EventAPI {

    @Multipart
    @POST("/events/create")
    Call<EventCustom> createEvent(@Header("Authorization") String token, @Part("title") RequestBody titleBody, @Part("description") RequestBody descriptionBody, @Part("time") RequestBody timeBody, @Part("place") RequestBody placeBody, @Part("authorID") RequestBody authorIDBody, @Part("scores") RequestBody scoresBody, @Part("maxUsers") RequestBody maxUsersBody, @Part("currentUsers") RequestBody currentUsersBody, @Part("lat") RequestBody latBody, @Part("longt") RequestBody longtBody, @Part MultipartBody.Part img);

    @DELETE("/events/delete")
    Call<String> deleteEvent(@Header("Authorization") String token, @Query("id") String eventID);

    @GET("/events/getEventByID")
    Call<EventCustom> getEventByID(@Header("Authorization") String token, @Query("id") String eventID);

    @PUT("/events/addUserToEvent")
    Call<EventCustom> addUserToEvent(@Header("Authorization") String token, @Body AddUserToEventDTO addUserToEventDTO);

    @GET("/events/all")
    Call<EventsListDTO> getEventsList(@Header("Authorization") String token);

    @PUT("/events/removeUserToEvent")
    Call<EventCustom> removeUserToEvent(@Header("Authorization") String token, @Body AddUserToEventDTO removeUserToEventDTO);

    @GET("/events/findEventsByAuthorID")
    Call<EventsListDTO> findEventsByAuthorID(@Header("Authorization") String token, @Query("authorID") String authorID);

    @GET("/events/findAuthorsEvents")
    Call<EventsListDTO> findAuthorsEvents(@Header("Authorization") String token, @Query("authorID") String authorID);

    @GET("/events/findNearestEventsByAuthorCoords")
    Call<EventsListDTO> findNearestEventsByAuthorCoords(@Header("Authorization") String token, @Query("lat") double lat, @Query("longt") double longt);

    @GET("/events/searchPosts")
    Call<SearchDTO> getPosts(@Header("Authorization") String token, @Query("title") String title);

    @GET("/events/getUsersFromEvents")
    Call<UsersListDTO> getUsersFromEvents(@Header("Authorization") String token, @Query("authorID") String authorID, @Query("eventID") String eventID);

    @POST("/events/create_comment")
    Call<Comment> createComment(@Header("Authorization") String token, @Body Comment comment);

    @GET("/events/get_comments")
    Call<CommentsDTO> getComments(@Header("Authorization") String token, @Query("eventID") String eventID);

    @DELETE("/events/delete_comment")
    Call<ResponseBody> deleteComment(@Header("Authorization") String token, @Query("id") String id);

}

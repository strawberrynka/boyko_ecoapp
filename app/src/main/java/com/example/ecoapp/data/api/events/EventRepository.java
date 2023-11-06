package com.example.ecoapp.data.api.events;

import com.example.ecoapp.data.api.events.dto.AddUserToEventDTO;
import com.example.ecoapp.data.api.events.dto.CommentsDTO;
import com.example.ecoapp.data.api.events.dto.EventsListDTO;
import com.example.ecoapp.data.api.events.dto.SearchDTO;
import com.example.ecoapp.data.api.users.dto.UsersListDTO;
import com.example.ecoapp.data.models.Comment;
import com.example.ecoapp.data.models.EventCustom;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class EventRepository {
    private final EventAPI eventAPI;

    public EventRepository(EventAPIService eventAPIService) {
        eventAPI = eventAPIService.getEventAPI();
    }

    public Call<EventCustom> createEvent(String token, String title, File photo, String description, String time, String place, String authorID, Integer scores, Integer maxUsers, double lat, double longt) {
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), photo);
        MultipartBody.Part file = MultipartBody.Part.createFormData("img", photo.getName(), fileReqBody);

        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody timeBody = RequestBody.create(MediaType.parse("text/plain"), time);
        RequestBody placeBody = RequestBody.create(MediaType.parse("text/plain"), place);
        RequestBody authorIDBody = RequestBody.create(MediaType.parse("text/plain"), authorID);
        RequestBody scoresBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(scores));
        RequestBody maxUsersBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(maxUsers));
        RequestBody currentUsersBody = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody latBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(lat));
        RequestBody longtBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longt));

        return eventAPI.createEvent(token, titleBody, descriptionBody, timeBody, placeBody, authorIDBody, scoresBody, maxUsersBody, currentUsersBody, latBody, longtBody, file);
    }

    public Call<String> deleteEvent(String token, String eventID) {
        return eventAPI.deleteEvent(token, eventID);
    }

    public Call<EventCustom> getEventByID(String token, String eventID) {
        return eventAPI.getEventByID(token, eventID);
    }

    public Call<EventCustom> addUserToEvent(String token, String eventID, String authorID) {
        return eventAPI.addUserToEvent(token, new AddUserToEventDTO(eventID, authorID));
    }

    public Call<EventCustom> removeUserToEvent(String token, String eventID, String authorID) {
        return eventAPI.removeUserToEvent(token, new AddUserToEventDTO(eventID, authorID));
    }

    public Call<EventsListDTO> getEventsList(String token) {
        return eventAPI.getEventsList(token);
    }

    public Call<EventsListDTO> findEventsByAuthorID(String token, String authorID) {
        return eventAPI.findEventsByAuthorID(token, authorID);
    }

    public Call<EventsListDTO> findNearestEventsByAuthorCoords(String token, double lat, double longt) {
        return eventAPI.findNearestEventsByAuthorCoords(token, lat, longt);
    }

    public Call<EventsListDTO> findAuthorsEvents(String token, String authorID) {
        return eventAPI.findAuthorsEvents(token, authorID);
    }

    public Call<UsersListDTO> getUsersFromEvents(String token, String authorID, String eventID) {
        return eventAPI.getUsersFromEvents(token, authorID, eventID);
    }

    public Call<SearchDTO> getPosts(String token, String title) {
        return eventAPI.getPosts(token, title);
    }

    public Call<CommentsDTO> getComments(String token, String eventID) {
        return eventAPI.getComments(token, eventID);
    }

    public Call<Comment> createComment(String token, String authorID, String eventID, String content) {
        return eventAPI.createComment(token, new Comment(authorID, eventID, content));
    }

    public Call<ResponseBody> deleteComment(String token, String id) {
        return eventAPI.deleteComment(token, id);
    }
}

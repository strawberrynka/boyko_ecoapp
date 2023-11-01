package com.example.ecoapp.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecoapp.data.api.RetrofitService;
import com.example.ecoapp.data.api.events.EventAPIService;
import com.example.ecoapp.data.api.events.EventRepository;
import com.example.ecoapp.data.api.events.dto.CommentsDTO;
import com.example.ecoapp.data.api.events.dto.EventsListDTO;
import com.example.ecoapp.data.api.events.dto.SearchDTO;
import com.example.ecoapp.data.api.users.dto.UsersListDTO;
import com.example.ecoapp.data.models.Comment;
import com.example.ecoapp.data.models.Search;
import com.example.ecoapp.data.models.User;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.data.models.EventCustom;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventViewModel extends AndroidViewModel {
    private final EventRepository eventRepository;
    private final StorageHandler storageHandler;
    private final MutableLiveData<ArrayList<EventCustom>> eventsList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<EventCustom>> eventsList2 = new MutableLiveData<>();
    private final MutableLiveData<Integer> statusCode = new MutableLiveData<>(0);
    private final MutableLiveData<EventCustom> event = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isNavigation = new MutableLiveData<>(false);
    private final MutableLiveData<ArrayList<User>> usersScoresList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isGetContext = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> isLoadData = new MutableLiveData<>(0);
    private final MutableLiveData<ArrayList<Search>> searchPosts = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Comment>> commentsList = new MutableLiveData<>();

    public EventViewModel(@NonNull Application application) {
        super(application);

        storageHandler = new StorageHandler(application);
        eventRepository = new EventRepository(new EventAPIService(new RetrofitService()));
    }

    public LiveData<Integer> sendData(String title, String description, String date, String time, File file, String maxPeople, String address, double lat, double longt, int scores) {
        eventRepository.createEvent(storageHandler.getToken(), title, file, description, date + " " + time, address, storageHandler.getUserID(), scores, maxPeople.isEmpty() ? 0 : Integer.parseInt(maxPeople), lat, longt).enqueue(new Callback<EventCustom>() {
            @Override
            public void onResponse(@NotNull Call<EventCustom> call, @NotNull Response<EventCustom> response) {
                statusCode.setValue(response.code());
                isNavigation.setValue(true);
            }

            @Override
            public void onFailure(@NotNull Call<EventCustom> call, @NotNull Throwable t) {
                t.printStackTrace();
                statusCode.setValue(400);
            }
        });

        return statusCode;
    }

    public LiveData<ArrayList<EventCustom>> getEventsList() {
        isGetContext.setValue(false);
        eventRepository.getEventsList(storageHandler.getToken()).enqueue(new Callback<EventsListDTO>() {
            @Override
            public void onResponse(@NotNull Call<EventsListDTO> call, @NotNull Response<EventsListDTO> response) {
                statusCode.setValue(response.code());
                if (response.isSuccessful() && response.body() != null && response.body().getItem() != null) {
                    eventsList.setValue(response.body().getItem());
                    isGetContext.setValue(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventsListDTO> call, @NotNull Throwable t) {
                statusCode.setValue(400);
                isGetContext.setValue(false);
                t.printStackTrace();
            }
        });

        return eventsList;
    }

    public LiveData<EventCustom> getEventByID(String id) {
        eventRepository.getEventByID(storageHandler.getToken(), id).enqueue(new Callback<EventCustom>() {
            @Override
            public void onResponse(@NotNull Call<EventCustom> call, @NotNull Response<EventCustom> response) {
                statusCode.setValue(response.code());
                if (response.isSuccessful() && response.body() != null) {
                    event.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventCustom> call, @NotNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return event;
    }

    public LiveData<Integer> enroll(String eventID) {
        statusCode.setValue(0);
        eventRepository.addUserToEvent(storageHandler.getToken(), eventID, storageHandler.getUserID()).enqueue(new Callback<EventCustom>() {
            @Override
            public void onResponse(@NotNull Call<EventCustom> call, @NotNull Response<EventCustom> response) {
                statusCode.setValue(response.code());
            }

            @Override
            public void onFailure(@NotNull Call<EventCustom> call, @NotNull Throwable t) {
                t.printStackTrace();
                statusCode.setValue(400);
            }
        });

        return statusCode;
    }

    public LiveData<Integer> deleteEvent(String eventID) {
        statusCode.setValue(0);
        eventRepository.deleteEvent(storageHandler.getToken(), eventID).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                statusCode.setValue(response.code());
                if (response.isSuccessful()) isNavigation.setValue(true);
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                statusCode.setValue(400);
                t.printStackTrace();
            }
        });

        return statusCode;
    }

    public LiveData<Integer> refusePeople(String eventID) {
        statusCode.setValue(0);

        eventRepository.removeUserToEvent(storageHandler.getToken(), eventID, storageHandler.getUserID()).enqueue(new Callback<EventCustom>() {
            @Override
            public void onResponse(@NotNull Call<EventCustom> call, @NotNull Response<EventCustom> response) {
                statusCode.setValue(response.code());
            }

            @Override
            public void onFailure(@NotNull Call<EventCustom> call, @NotNull Throwable t) {
                t.printStackTrace();
                statusCode.setValue(400);
            }
        });

        return statusCode;
    }

    public LiveData<ArrayList<EventCustom>> findEventsByAuthorID() {
        eventRepository.findEventsByAuthorID(storageHandler.getToken(), storageHandler.getUserID()).enqueue(new Callback<EventsListDTO>() {
            @Override
            public void onResponse(@NotNull Call<EventsListDTO> call, @NotNull Response<EventsListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    eventsList.setValue(response.body().getItem());
                    if (isLoadData.getValue() != null) isLoadData.setValue(isLoadData.getValue() + 1);
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventsListDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return eventsList;
    }

    public LiveData<ArrayList<EventCustom>> findAuthorsEvents() {
        eventRepository.findAuthorsEvents(storageHandler.getToken(), storageHandler.getUserID()).enqueue(new Callback<EventsListDTO>() {
            @Override
            public void onResponse(@NotNull Call<EventsListDTO> call, @NotNull Response<EventsListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    eventsList2.setValue(response.body().getItem());
                    if (isLoadData.getValue() != null) isLoadData.setValue(isLoadData.getValue() + 1);
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventsListDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return eventsList2;
    }


    public LiveData<ArrayList<EventCustom>> findNearestEventsByAuthorCoords(double lat, double longt) {
        eventRepository.findNearestEventsByAuthorCoords(storageHandler.getToken(), lat, longt).enqueue(new Callback<EventsListDTO>() {
            @Override
            public void onResponse(@NotNull Call<EventsListDTO> call, @NotNull Response<EventsListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    eventsList.setValue(response.body().getItem());
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventsListDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return eventsList;
    }

    public LiveData<ArrayList<User>> getUsersScores(String eventID) {
        eventRepository.getUsersFromEvents(storageHandler.getToken(), storageHandler.getUserID(), eventID).enqueue(new Callback<UsersListDTO>() {
            @Override
            public void onResponse(@NotNull Call<UsersListDTO> call, @NotNull Response<UsersListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usersScoresList.setValue(response.body().getItem());
                }
            }

            @Override
            public void onFailure(@NotNull Call<UsersListDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return usersScoresList;
    }

    public LiveData<ArrayList<Search>> getPosts(String title) {
        eventRepository.getPosts(storageHandler.getToken(), title).enqueue(new Callback<SearchDTO>() {
            @Override
            public void onResponse(@NotNull Call<SearchDTO> call, @NotNull Response<SearchDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    searchPosts.setValue(response.body().getSearches());
                    isNavigation.setValue(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SearchDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return searchPosts;
    }

    public LiveData<ArrayList<Comment>> getCommentsList(String eventId) {
        eventRepository.getComments(storageHandler.getToken(), eventId).enqueue(new Callback<CommentsDTO>() {
            @Override
            public void onResponse(@NotNull Call<CommentsDTO> call, @NotNull Response<CommentsDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    commentsList.setValue(response.body().getItem());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommentsDTO> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return commentsList;
    }

    public LiveData<Integer> deleteComment(String id) {
        statusCode.setValue(0);
        eventRepository.deleteComment(storageHandler.getToken(), id).enqueue(new Callback<ResponseBody>() {
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

    public LiveData<ArrayList<Comment>> createComment(String eventID, String content) {
        eventRepository.createComment(storageHandler.getToken(), storageHandler.getUserID(), eventID, content).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(@NotNull Call<Comment> call, @NotNull Response<Comment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Comment> list = commentsList.getValue() == null ? new ArrayList<>() : commentsList.getValue();
                    list.add(response.body());
                    commentsList.setValue(list);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Comment> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return commentsList;
    }

    public void clearEvents() {
        eventsList.setValue(null);
    }

    public LiveData<Boolean> getNavigate() {
        return isNavigation;
    }

    public void cancelNavigate() {
        isNavigation.setValue(false);
    }

    public void setCancelContext() {
        isGetContext.setValue(false);
    }

    public LiveData<Boolean> getIsContext() {
        return isGetContext;
    }

    public LiveData<Integer> getIsLoadData() {
        return isLoadData;
    }

    public LiveData<Integer> clearLoadData() {
        isLoadData.setValue(0);

        return isLoadData;
    }
}

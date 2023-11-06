package com.example.ecoapp.data.api.users;

import com.example.ecoapp.data.api.users.dto.AddGuideToUserDTO;
import com.example.ecoapp.data.models.User;
import com.example.ecoapp.data.api.users.dto.ChangeScoresDTO;
import com.example.ecoapp.data.api.users.dto.EditProfileDTO;
import com.example.ecoapp.data.api.users.dto.HabitDTO;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class UserRepository {
    private final UserAPI userAPI;

    public UserRepository(UserAPIService userApiService) {
        userAPI = userApiService.getUserAPI();
    }

    public Call<User> editProfile(String token, String id, String name, String old_password, String new_password) {
        return userAPI.editProfile(token, new EditProfileDTO(id, name, old_password, new_password));
    }

    public Call<User> changeScores(String token, String authorID, String id, String eventID) {
        return userAPI.changeScores(token, new ChangeScoresDTO(id, eventID, authorID));
    }

    public Call<User> getUser(String token, String id) {
        return userAPI.getUser(token, id);
    }

    public Call<User> addPhoto(String token, String id, File photo) {
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), photo);
        MultipartBody.Part file = MultipartBody.Part.createFormData("img", photo.getName(), fileReqBody);

        return userAPI.addPhoto(token, file);
    }

    public Call<User> addHabit(String token, String id, String title) {
        return userAPI.addHabit(token, new HabitDTO(id, title));
    }

    public Call<User> removeHabit(String token, String id, String title) {
        return userAPI.removeHabit(token, new HabitDTO(id, title));
    }

    public Call<User> getHabitByTitle(String token, String title) {
        return userAPI.getHabitByTitle(token, title);
    }

    public Call<User> updateGuideToUser(String token, String userID, String guideID) {
        return userAPI.updateGuideToUser(token, new AddGuideToUserDTO(userID, guideID));
    }

    public Call<ResponseBody> updateLogin(String token, String login) {
        return userAPI.editLogin(token, new User(login));
    }
}

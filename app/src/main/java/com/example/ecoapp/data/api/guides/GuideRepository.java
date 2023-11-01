package com.example.ecoapp.data.api.guides;

import com.example.ecoapp.data.api.guides.dto.GuidesListDTO;
import com.example.ecoapp.data.models.Guide;
import com.example.ecoapp.data.models.Rating;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Query;

public class GuideRepository {
    private final GuideAPI guideAPI;

    public GuideRepository(GuideAPIService guideAPIService) {
        guideAPI = guideAPIService.getGuideAPI();
    }

    public Call<Guide> createGuide(String token, String title, String description, String authorID, String source, File photo) {
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), photo);
        MultipartBody.Part file = MultipartBody.Part.createFormData("img", photo.getName(), fileReqBody);

        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody authorIDBody = RequestBody.create(MediaType.parse("text/plain"), authorID);
        RequestBody sourceBody = RequestBody.create(MediaType.parse("text/plain"), source);

        return guideAPI.createGuide(token, titleBody, descriptionBody, authorIDBody, sourceBody, file);
    }

    public Call<ResponseBody> deleteGuide(String token, String guideID) {
        return guideAPI.deleteHabit(token, guideID);
    }

    public Call<Guide> getGuideByID(String token, String guideID) {
        return guideAPI.getGuideByID(token, guideID);
    }

    public Call<ArrayList<Guide>> getGuidesList(String token) {
        return guideAPI.getGuidesList(token);
    }

    public Call<ResponseBody> changeGuide(String token, String title, String description, String source, File photo) {
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), photo);
        MultipartBody.Part file = MultipartBody.Part.createFormData("img", photo.getName(), fileReqBody);

        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody sourceBody = RequestBody.create(MediaType.parse("text/plain"), source);

        return guideAPI.changeGuide(token, titleBody, descriptionBody, sourceBody, file);
    }

    public Call<Rating> getRating(String token, String guideID, String authorID) {
        return guideAPI.getRating(token, guideID, authorID);
    }

    public Call<ResponseBody> setRating(String token, String guideID, String authorID, float mark) {
        return guideAPI.setRating(token, new Rating(authorID, mark, guideID));
    }

    public Call<GuidesListDTO> getGuidesSavedList(String token, String authorID) {
        return guideAPI.getGuidesSavedList(token, authorID);
    }
}
package com.example.ecoapp.domain.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ecoapp.data.models.EventCustom;

public class StorageHandler {
    private SharedPreferences sharedPreferences;

    public StorageHandler(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences("prms", Context.MODE_PRIVATE);
    }

    public void logout() {
        int theme = sharedPreferences.getInt("theme", 0);
        sharedPreferences.edit().clear().apply();
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("theme", theme);
        edit.apply();
    }

    public boolean getAuth() {
        return !sharedPreferences.getString("token", "").isEmpty();
    }

    public void setToken(String token) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("token", token);
        edit.apply();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    public void saveUserID(String userID) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("userID", userID);
        edit.apply();
    }

    public String getUserID() {
        return sharedPreferences.getString("userID", "");
    }

    public void setTheme(int themeNum) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("theme", themeNum);
        edit.apply();
    }

    public int getTheme() {
        return sharedPreferences.getInt("theme", 0);
    }

    public void saveIntermediateData(String title, String description, String data, String time, int peopleLen, String pathImage, int scores) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("eventTitle", title);
        edit.putString("eventDescription", description);
        edit.putString("eventData", data);
        edit.putString("eventTime", time);
        edit.putInt("eventPeopleLen", peopleLen);
        edit.putInt("eventScores", scores);
        edit.putString("eventImagePath", pathImage);

        edit.apply();
    }

    public EventCustom getIntermediateData() {
        EventCustom eventCustom = new EventCustom();
        eventCustom.setTitle(sharedPreferences.getString("eventTitle", ""));
        eventCustom.setDescription(sharedPreferences.getString("eventDescription", ""));
        eventCustom.setDate(sharedPreferences.getString("eventData", ""));
        eventCustom.setTime(sharedPreferences.getString("eventTime", ""));
        eventCustom.setMaxUsers(sharedPreferences.getInt("eventPeopleLen", 0));
        eventCustom.setScores(sharedPreferences.getInt("eventScores", 0));
        eventCustom.setPhoto(sharedPreferences.getString("eventImagePath", ""));

        return eventCustom;
    }

    public void clearIntermediateData() {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove("eventTitle");
        edit.remove("eventDescription");
        edit.remove("eventData");
        edit.remove("eventTime");
        edit.remove("eventPeopleLen");
        edit.remove("eventImagePath");
        edit.apply();
    }
}

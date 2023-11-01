package com.example.ecoapp.presentation;

import android.app.Application;

import com.example.ecoapp.BuildConfig;
import com.yandex.mapkit.MapKitFactory;

public class ApplicationCore extends Application {
    private boolean isInitMap = false;

    @Override
    public void onCreate() {
        super.onCreate();

        if (!isInitMap) {
            MapKitFactory.setApiKey(BuildConfig.apiKey);
            MapKitFactory.initialize(getApplicationContext());
            isInitMap = true;
        }

    }
}

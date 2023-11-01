package com.example.ecoapp.data.api.guides;

import com.example.ecoapp.data.api.RetrofitService;

public class GuideAPIService {
    private final GuideAPI guideAPI;

    public GuideAPIService(RetrofitService retrofitService) {
        guideAPI = retrofitService.getInstance().create(GuideAPI.class);
    }

    public GuideAPI getGuideAPI() {
        return guideAPI;
    }
}

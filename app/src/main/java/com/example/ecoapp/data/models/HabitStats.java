package com.example.ecoapp.data.models;

import com.google.gson.annotations.SerializedName;

public class HabitStats {
    @SerializedName("date")
    private String date;

    @SerializedName("count")
    private int count;

    @SerializedName("maxCount")
    private int maxCount;

    @SerializedName("day")
    private int day;

    @SerializedName("month")
    private int month;

    @SerializedName("year")
    private int year;

    public HabitStats() {}

    public HabitStats(String date, int count, int maxCount, int day, int month, int year) {
        this.date = date;
        this.count = count;
        this.maxCount = maxCount;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

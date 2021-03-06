package com.example.heaapp.model.user_information;

import com.example.heaapp.model.food.Dishes;
import com.example.heaapp.model.workout.Activities;

import io.realm.RealmList;
import io.realm.RealmObject;

public class DailySummary extends RealmObject {
    private long id;
    private String date;
    private String month;
    private String year;
    private long waterConsume;
    private long eatenCalories;
    private long burnedCalories;
    private long neededCalories;
    private long eatenCarbs;
    private long eatenProtein;
    private long eatenFat;
    private RealmList<Dishes> breakfastDishes;
    private RealmList<Dishes> launchDishes;
    private RealmList<Dishes> dinnerDishes;
    private RealmList<Activities> activities;
    public DailySummary() {
    }

    public DailySummary(long id, String date, String month, String year, long waterConsume, long eatenCalories, long burnedCalories, long neededCalories, long eatenCarbs, long eatenProtein, long eatenFat, RealmList<Dishes> breakfastDishes, RealmList<Dishes> launchDishes, RealmList<Dishes> dinnerDishes, RealmList<Activities> activities) {
        this.id = id;
        this.date = date;
        this.month = month;
        this.year = year;
        this.waterConsume = waterConsume;
        this.eatenCalories = eatenCalories;
        this.burnedCalories = burnedCalories;
        this.neededCalories = neededCalories;
        this.eatenCarbs = eatenCarbs;
        this.eatenProtein = eatenProtein;
        this.eatenFat = eatenFat;
        this.breakfastDishes = breakfastDishes;
        this.launchDishes = launchDishes;
        this.dinnerDishes = dinnerDishes;
        this.activities = activities;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getEatenCalories() {
        return eatenCalories;
    }

    public void setEatenCalories(long eatenCalories) {
        this.eatenCalories = eatenCalories;
    }

    public long getBurnedCalories() {
        return burnedCalories;
    }

    public void setBurnedCalories(long burnedCalories) {
        this.burnedCalories = burnedCalories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWaterConsume() {
        return waterConsume;
    }

    public void setWaterConsume(long waterConsume) {
        this.waterConsume = waterConsume;
    }

    public long getNeededCalories() {
        return neededCalories;
    }

    public void setNeededCalories(long neededCalories) {
        this.neededCalories = neededCalories;
    }

    public long getEatenCarbs() {
        return eatenCarbs;
    }

    public void setEatenCarbs(long eatenCarbs) {
        this.eatenCarbs = eatenCarbs;
    }

    public long getEatenProtein() {
        return eatenProtein;
    }

    public void setEatenProtein(long eatenProtein) {
        this.eatenProtein = eatenProtein;
    }

    public long getEatenFat() {
        return eatenFat;
    }

    public void setEatenFat(long eatenFat) {
        this.eatenFat = eatenFat;
    }

    public RealmList<Dishes> getBreakfastDishes() {
        return breakfastDishes;
    }

    public void setBreakfastDishes(RealmList<Dishes> breakfastDishes) {
        this.breakfastDishes = breakfastDishes;
    }

    public RealmList<Dishes> getLaunchDishes() {
        return launchDishes;
    }

    public void setLaunchDishes(RealmList<Dishes> launchDishes) {
        this.launchDishes = launchDishes;
    }

    public RealmList<Dishes> getDinnerDishes() {
        return dinnerDishes;
    }

    public void setDinnerDishes(RealmList<Dishes> dinnerDishes) {
        this.dinnerDishes = dinnerDishes;
    }

    public RealmList<Activities> getActivities() {
        return activities;
    }

    public void setActivities(RealmList<Activities> activities) {
        this.activities = activities;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

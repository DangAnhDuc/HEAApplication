package com.example.heaapp.model.user_information;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DailySummary extends RealmObject {
    private long id;
    private String date;
    private long waterConsume;
    private long eatenCalories;
    private long burnedCalories;
    private long neededCalories;
    private long eatenCarbs;
    private long eatenProtein;
    private long eatenFat;

    public DailySummary() {
    }

    public DailySummary(long id, String date, long waterConsume, long eatenCalories, long burnedCalories, long neededCalories, long eatenCarbs, long eatenProtein, long eatenFat) {
        this.id = id;
        this.date = date;
        this.waterConsume = waterConsume;
        this.eatenCalories = eatenCalories;
        this.burnedCalories = burnedCalories;
        this.neededCalories = neededCalories;
        this.eatenCarbs = eatenCarbs;
        this.eatenProtein = eatenProtein;
        this.eatenFat = eatenFat;
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

    public void setEatenFat(long eateaFat) {
        this.eatenFat = eateaFat;
    }
}

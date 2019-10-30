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

    public DailySummary() {
    }

    public DailySummary(long id, String date, long waterConsume, long eatenCalories, long burnedCalories) {
        this.id = id;
        this.date = date;
        this.waterConsume = waterConsume;
        this.eatenCalories = eatenCalories;
        this.burnedCalories = burnedCalories;
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
}

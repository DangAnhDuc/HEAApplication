package com.example.heaapp.model.user_information;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DailySummary extends RealmObject {
    private long id;
    private int dayCount;
    private Date date;
    private long waterConsume;

    public DailySummary() {
    }

    public DailySummary(long id, int dayCount, Date date, long waterConsume) {
        this.id = id;
        this.dayCount = dayCount;
        this.date = date;
        this.waterConsume = waterConsume;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getWaterConsume() {
        return waterConsume;
    }

    public void setWaterConsume(long waterConsume) {
        this.waterConsume = waterConsume;
    }
}

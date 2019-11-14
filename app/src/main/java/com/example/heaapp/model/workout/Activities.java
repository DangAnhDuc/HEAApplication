package com.example.heaapp.model.workout;

import io.realm.RealmObject;

public class Activities extends RealmObject {
    String name;
    String time;
    long energy;

    public Activities() {
    }

    public Activities(String name, String time, long energy) {
        this.name = name;
        this.time = time;
        this.energy = energy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getEnergy() {
        return energy;
    }

    public void setEnergy(long energy) {
        this.energy = energy;
    }
}

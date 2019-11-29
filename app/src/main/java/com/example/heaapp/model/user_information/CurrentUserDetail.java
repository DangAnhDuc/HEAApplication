package com.example.heaapp.model.user_information;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentUserDetail extends RealmObject {
    private long id;
    private long weight;
    private long height;
    private int age;
    private String sex;
    private long waist;
    private long hip;
    private long chest;

    public CurrentUserDetail() {
    }

    public CurrentUserDetail(long id, long weight, long height, int age, String sex, long waist, long hip, long chest) {
        this.id = id;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.sex = sex;
        this.waist = waist;
        this.hip = hip;
        this.chest = chest;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public long getWaist() {
        return waist;
    }

    public void setWaist(long waist) {
        this.waist = waist;
    }

    public long getHip() {
        return hip;
    }

    public void setHip(long hip) {
        this.hip = hip;
    }

    public long getChest() {
        return chest;
    }

    public void setChest(long chest) {
        this.chest = chest;
    }
}

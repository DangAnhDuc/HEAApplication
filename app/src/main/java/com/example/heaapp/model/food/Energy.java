package com.example.heaapp.model.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Energy extends RealmObject {

    @SerializedName("name_translations")
    @Expose
    private NameTranslations_________ nameTranslations;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("per_hundred")
    @Expose
    private double perHundred;
    @SerializedName("per_portion")
    @Expose
    private double perPortion;
    @SerializedName("per_day")
    @Expose
    private double perDay;

    public NameTranslations_________ getNameTranslations() {
        return nameTranslations;
    }

    public void setNameTranslations(NameTranslations_________ nameTranslations) {
        this.nameTranslations = nameTranslations;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPerHundred() {
        return perHundred;
    }

    public void setPerHundred(double perHundred) {
        this.perHundred = perHundred;
    }

    public double getPerPortion() {
        return perPortion;
    }

    public void setPerPortion(double perPortion) {
        this.perPortion = perPortion;
    }

    public double getPerDay() {
        return perDay;
    }

    public void setPerDay(double perDay) {
        this.perDay = perDay;
    }

}
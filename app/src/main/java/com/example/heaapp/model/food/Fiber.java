package com.example.heaapp.model.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fiber {

    @SerializedName("name_translations")
    @Expose
    private NameTranslations___ nameTranslations;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("per_hundred")
    @Expose
    private Double perHundred;
    @SerializedName("per_portion")
    @Expose
    private Double perPortion;
    @SerializedName("per_day")
    @Expose
    private Double perDay;

    public NameTranslations___ getNameTranslations() {
        return nameTranslations;
    }

    public void setNameTranslations(NameTranslations___ nameTranslations) {
        this.nameTranslations = nameTranslations;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPerHundred() {
        return perHundred;
    }

    public void setPerHundred(Double perHundred) {
        this.perHundred = perHundred;
    }

    public Double getPerPortion() {
        return perPortion;
    }

    public void setPerPortion(Double perPortion) {
        this.perPortion = perPortion;
    }

    public Double getPerDay() {
        return perDay;
    }

    public void setPerDay(Double perDay) {
        this.perDay = perDay;
    }

}
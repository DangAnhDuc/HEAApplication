package com.example.heaapp.model.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Protein {

    @SerializedName("name_translations")
    @Expose
    private NameTranslations__ nameTranslations;
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
    private Integer perDay;

    public NameTranslations__ getNameTranslations() {
        return nameTranslations;
    }

    public void setNameTranslations(NameTranslations__ nameTranslations) {
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

    public Integer getPerDay() {
        return perDay;
    }

    public void setPerDay(Integer perDay) {
        this.perDay = perDay;
    }

}
package com.example.heaapp.model.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Energy {

    @SerializedName("name_translations")
    @Expose
    private NameTranslations_________ nameTranslations;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("per_hundred")
    @Expose
    private Integer perHundred;
    @SerializedName("per_portion")
    @Expose
    private Object perPortion;
    @SerializedName("per_day")
    @Expose
    private Integer perDay;

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

    public Integer getPerHundred() {
        return perHundred;
    }

    public void setPerHundred(Integer perHundred) {
        this.perHundred = perHundred;
    }

    public Object getPerPortion() {
        return perPortion;
    }

    public void setPerPortion(Object perPortion) {
        this.perPortion = perPortion;
    }

    public Integer getPerDay() {
        return perDay;
    }

    public void setPerDay(Integer perDay) {
        this.perDay = perDay;
    }

}
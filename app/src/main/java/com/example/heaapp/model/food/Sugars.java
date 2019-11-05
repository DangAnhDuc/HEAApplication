package com.example.heaapp.model.food;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sugars {

    @SerializedName("name_translations")
    @Expose
    private NameTranslations____ nameTranslations;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("per_hundred")
    @Expose
    private Integer perHundred;
    @SerializedName("per_portion")
    @Expose
    private Double perPortion;
    @SerializedName("per_day")
    @Expose
    private Integer perDay;

    public NameTranslations____ getNameTranslations() {
        return nameTranslations;
    }

    public void setNameTranslations(NameTranslations____ nameTranslations) {
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
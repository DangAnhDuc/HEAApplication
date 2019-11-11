package com.example.heaapp.model.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class NameTranslations_________ extends RealmObject {

    @SerializedName("de")
    @Expose
    private String de;
    @SerializedName("en")
    @Expose
    private String en;
    @SerializedName("fr")
    @Expose
    private String fr;
    @SerializedName("it")
    @Expose
    private String it;

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getIt() {
        return it;
    }

    public void setIt(String it) {
        this.it = it;
    }

}
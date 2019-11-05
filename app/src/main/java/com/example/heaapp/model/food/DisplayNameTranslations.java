package com.example.heaapp.model.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisplayNameTranslations {

    @SerializedName("de")
    @Expose
    private String de;
    @SerializedName("fr")
    @Expose
    private String fr;
    @SerializedName("it")
    @Expose
    private String it;
    @SerializedName("en")
    @Expose
    private String en;

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
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

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

}
package com.example.heaapp.model.food;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Image extends RealmObject {

    @SerializedName("categories")
    @Expose
    private RealmList<String> categories = null;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("large")
    @Expose
    private String large;
    @SerializedName("xlarge")
    @Expose
    private String xlarge;

    public RealmList<String> getCategories() {
        return categories;
    }

    public void setCategories(RealmList<String> categories) {
        this.categories = categories;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getXlarge() {
        return xlarge;
    }

    public void setXlarge(String xlarge) {
        this.xlarge = xlarge;
    }
}


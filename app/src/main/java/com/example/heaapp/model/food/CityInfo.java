package com.example.heaapp.model.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityInfo {

    @SerializedName("data")
    @Expose
    private Information data;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public Information getData() {
        return data;
    }

    public void setData(Information data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
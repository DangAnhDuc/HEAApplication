package com.example.heaapp.model.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodInfor {

    @SerializedName("data")
    @Expose
    private List<Information> informationList;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Information> getInformationList() {
        return informationList;
    }

    public void setData(List<Information> informationList) {
        this.informationList = informationList;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
package com.example.heaapp.model.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("api_version")
    @Expose
    private String apiVersion;
    @SerializedName("generated_in")
    @Expose
    private Integer generatedIn;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public Integer getGeneratedIn() {
        return generatedIn;
    }

    public void setGeneratedIn(Integer generatedIn) {
        this.generatedIn = generatedIn;
    }

}
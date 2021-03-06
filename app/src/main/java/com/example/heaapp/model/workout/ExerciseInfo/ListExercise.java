package com.example.heaapp.model.workout.ExerciseInfo;

import java.util.List;

import com.example.heaapp.model.workout.ExerciseInfo.ItemExercise;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListExercise {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private List<ItemExercise> item = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<ItemExercise> getResults() {
        return item;
    }

    public void setResults(List<ItemExercise> item) {
        this.item = item;
    }

}
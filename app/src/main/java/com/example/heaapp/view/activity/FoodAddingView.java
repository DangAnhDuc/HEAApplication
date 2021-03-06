package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.food.Data;

import java.util.List;

public interface FoodAddingView extends BaseView {
    void crawlDataSuccess(List<Data> foodList);
    void addFoodSuccessfully();
    void addFoodFailed();

}

package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.food.Information;

import java.util.List;

public interface FoodAddingView extends BaseView {
    void crawlDataFailed(String message);
    void crawlDataSuccess(List<Information> informationList);
}
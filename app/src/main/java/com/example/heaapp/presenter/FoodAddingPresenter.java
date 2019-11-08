package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.activity.FoodAddingView;

public interface FoodAddingPresenter extends BasePresenter<FoodAddingView> {
    void crawlFoodData();
}

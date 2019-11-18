package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.model.food.Data;
import com.example.heaapp.view.activity.FoodAddingView;

public interface FoodAddingPresenter extends BasePresenter<FoodAddingView> {
    void crawlFoodData();
    void addDishes(Data data, String foodTime);

    void addDishesCustom(String foodTime,String name, String energy, String carbs, String protein, String fat);
}

package com.example.heaapp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.heaapp.model.food.Data;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.view.activity.FoodAddingView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class FoodAddingPresenterImpl implements  FoodAddingPresenter {
    private FoodAddingView foodAddingView;
    private Context context;
    private RealmService realmService;

    public FoodAddingPresenterImpl(FoodAddingView foodAddingView, Context context, RealmService realmService) {
        this.foodAddingView = foodAddingView;
        this.context = context;
        this.realmService = realmService;
    }

    @Override
    public void attachView(FoodAddingView view) {
        foodAddingView = view;
    }

    @Override
    public void detachView() {
        foodAddingView = null;
    }

    @Override
    public void crawlFoodData() {
        List<Data> foodList=Common.foodList;
        foodAddingView.crawlDataSuccess(foodList);
    }
}

package com.example.heaapp.presenter;

import android.content.Context;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.food.Data;
import com.example.heaapp.model.food.Dishes;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.view.activity.FoodAddingView;

import java.util.List;

public class FoodAddingPresenterImpl implements  FoodAddingPresenter, OnTransactionCallback {
    private FoodAddingView foodAddingView;
    private Context context;
    private RealmService realmService;
    private String foodTime;
    private Dishes dishes;
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

    @Override
    public void addDishes(Data data, String foodTime) {
        this.foodTime=foodTime;
        Long energy;
        try {
            energy= data.getNutrients().getEnergyKcal().getPerHundred().longValue();
        }
        catch (Exception e){
            energy= 0L;
        }

        Long carbs;
        try {
            carbs= data.getNutrients().getCarbohydrates().getPerHundred().longValue();
        }
        catch (Exception e){
            carbs= 0L;
        }

        Long protein;
        try {
            protein= data.getNutrients().getProtein().getPerHundred().longValue();
        }
        catch (Exception e){
            protein= 0L;
        }

        Long fat;
        try {
            fat= data.getNutrients().getFat().getPerHundred().longValue();
        }
        catch (Exception e){
            fat= 0L;
        }
        this.dishes=new Dishes(data.getDisplayNameTranslations().getEn(),energy,carbs,protein,fat);
        updateNutrition(energy,carbs,protein,fat);
    }

    @Override
    public void addDishesCustom(String foodTime,String name, String energy, String carbs, String protein, String fat) {
        this.foodTime=foodTime;
        this.dishes=new Dishes(name,Long.valueOf(energy),Long.valueOf(carbs),Long.valueOf(protein),Long.valueOf(fat));
        updateNutrition(Long.valueOf(energy),Long.valueOf(carbs),Long.valueOf(protein),Long.valueOf(fat));
    }

    private void updateNutrition(long energy,long carbs,long protein,long fat){
        double totalNeededCalories= realmService.getCurrentDate().get(0).getNeededCalories();
        double totalCaloriesEaten = realmService.getCurrentDate().get(0).getEatenCalories();
        double totalCarbsEaten = realmService.getCurrentDate().get(0).getEatenCarbs();
        double totalProteinEaten = realmService.getCurrentDate().get(0).getEatenProtein();
        double totalFatEaten = realmService.getCurrentDate().get(0).getEatenFat();

        totalCaloriesEaten=totalCaloriesEaten+energy;
        totalNeededCalories=totalNeededCalories-energy;
        totalCarbsEaten=totalCarbsEaten+carbs;
        totalProteinEaten=totalProteinEaten+protein;
        totalFatEaten=totalFatEaten+fat;
        realmService.updateNutrition((Double.valueOf(totalNeededCalories)).longValue(),(Double.valueOf(totalCaloriesEaten)).longValue(),
                (Double.valueOf(totalCarbsEaten)).longValue(),(Double.valueOf(totalProteinEaten)).longValue(),(Double.valueOf(totalFatEaten)).longValue(),this);

    }

    @Override
    public void onTransactionSuccess() {
        switch (foodTime){
            case "Breakfast":
                Common.breakfastDishes.add(dishes);
                break;
            case "Launch":
                Common.launchDishes.add(dishes);
                break;
            case "Dinner":
                Common.dinnerDishes.add(dishes);
                break;
        }
        foodAddingView.addFoodSuccessfully();
    }

    @Override
    public void onTransactionError(Exception e) {
        foodAddingView.addFoodFailed();

    }
}

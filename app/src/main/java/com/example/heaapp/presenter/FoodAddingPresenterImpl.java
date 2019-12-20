package com.example.heaapp.presenter;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.food.Data;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.view.activity.FoodAddingView;

import java.util.List;
import java.util.Objects;

public class FoodAddingPresenterImpl implements FoodAddingPresenter, OnTransactionCallback {
    private FoodAddingView foodAddingView;
    private RealmService realmService;
    private String foodTime;

    public FoodAddingPresenterImpl(FoodAddingView foodAddingView, RealmService realmService) {
        this.foodAddingView = foodAddingView;
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
        List<Data> foodList = Common.foodList;
        foodAddingView.crawlDataSuccess(foodList);
    }

    @Override
    public void addDishes(Data data, String foodTime) {
        this.foodTime = foodTime;
        long energy;
        try {
            energy = data.getNutrients().getEnergyKcal().getPerHundred().longValue();
        } catch (Exception e) {
            energy = 0L;
        }

        long carbs;
        try {
            carbs = data.getNutrients().getCarbohydrates().getPerHundred().longValue();
        } catch (Exception e) {
            carbs = 0L;
        }

        long protein;
        try {
            protein = data.getNutrients().getProtein().getPerHundred().longValue();
        } catch (Exception e) {
            protein = 0L;
        }

        long fat;
        try {
            fat = data.getNutrients().getFat().getPerHundred().longValue();
        } catch (Exception e) {
            fat = 0L;
        }
        updateNutrition(data.getDisplayNameTranslations().getEn(), energy, carbs, protein, fat);
    }

    @Override
    public void addDishesCustom(String foodTime, String name, String energy, String carbs, String protein, String fat) {
        this.foodTime = foodTime;
        try {
            updateNutrition(name, Long.valueOf(energy), Long.valueOf(carbs), Long.valueOf(protein), Long.valueOf(fat));
        } catch (Exception e) {
            foodAddingView.addFoodFailed();
        }
    }

    private void updateNutrition(String name, long energy, long carbs, long protein, long fat) {
        double totalNeededCalories = Objects.requireNonNull(realmService.getCurrentDate().get(0)).getNeededCalories();
        double totalCaloriesEaten = Objects.requireNonNull(realmService.getCurrentDate().get(0)).getEatenCalories();
        double totalCarbsEaten = Objects.requireNonNull(realmService.getCurrentDate().get(0)).getEatenCarbs();
        double totalProteinEaten = Objects.requireNonNull(realmService.getCurrentDate().get(0)).getEatenProtein();
        double totalFatEaten = Objects.requireNonNull(realmService.getCurrentDate().get(0)).getEatenFat();

        totalCaloriesEaten = totalCaloriesEaten + energy;
        totalNeededCalories = totalNeededCalories - energy;
        totalCarbsEaten = totalCarbsEaten + carbs;
        totalProteinEaten = totalProteinEaten + protein;
        totalFatEaten = totalFatEaten + fat;
        realmService.updateNutrition((Double.valueOf(totalNeededCalories)).longValue(), (Double.valueOf(totalCaloriesEaten)).longValue(),
                (Double.valueOf(totalCarbsEaten)).longValue(), (Double.valueOf(totalProteinEaten)).longValue(), (Double.valueOf(totalFatEaten)).longValue(), foodTime,
                name, energy, carbs, protein, fat, this);

    }

    @Override
    public void onTransactionSuccess() {
        foodAddingView.addFoodSuccessfully();
    }

    @Override
    public void onTransactionError(Exception e) {
        foodAddingView.addFoodFailed();

    }
}

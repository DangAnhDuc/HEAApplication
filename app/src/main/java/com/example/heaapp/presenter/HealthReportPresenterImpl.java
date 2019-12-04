package com.example.heaapp.presenter;

import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.view.activity.HealthReportView;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

import io.realm.RealmResults;

public class HealthReportPresenterImpl implements HealthReportPresenter {
    private HealthReportView healthReportView;
    private PieChart energyPieChart;
    private PieChart nutritionPieChart;
    private RealmService realmService;


    public HealthReportPresenterImpl(HealthReportView healthReportView, PieChart energyPieChart,PieChart nutritionPieChart,RealmService realmService) {
        this.healthReportView = healthReportView;
        this.energyPieChart = energyPieChart;
        this.nutritionPieChart=nutritionPieChart;
        this.realmService=realmService;
    }

    @Override
    public void attachView(HealthReportView view) {
        healthReportView=view;
    }

    @Override
    public void detachView() {
        healthReportView=null;
    }

    @Override
    public void getEnergyDataset() {
        RealmResults<DailySummary> realmResults = realmService.getCurrentDate();
        float[] yData= {realmResults.get(0).getEatenCalories(),realmResults.get(0).getBurnedCalories()};
        String[] xData = {"Energy Eaten", "Energy Burned"};
        healthReportView.createPieChart(energyPieChart,yData,xData);
    }

    @Override
    public void getNutritionDataset() {
        RealmResults<DailySummary> realmResults = realmService.getCurrentDate();
        float[] yData= {realmResults.get(0).getEatenCarbs(),realmResults.get(0).getEatenFat(),realmResults.get(0).getEatenProtein()};
        String[] xData = {"Carbohydrate", "Fat","Protein"};
        healthReportView.createPieChart(nutritionPieChart,yData,xData);
    }
}

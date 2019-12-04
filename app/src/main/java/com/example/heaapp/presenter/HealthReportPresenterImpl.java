package com.example.heaapp.presenter;

import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.view.activity.HealthReportView;
import com.github.mikephil.charting.charts.PieChart;
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
    public void getDailySummaryData() {
        RealmResults<DailySummary> realmResults = realmService.getCurrentDate();
        float[] yEnergyData= {realmResults.get(0).getEatenCalories(),realmResults.get(0).getBurnedCalories()};
        String[] xEnergyData = {"Energy Eaten", "Energy Burned"};
        healthReportView.createPieChart(energyPieChart,yEnergyData,xEnergyData);

        float[] yNutritionData= {realmResults.get(0).getEatenCarbs(),realmResults.get(0).getEatenFat(),realmResults.get(0).getEatenProtein()};
        String[] xNutritionData = {"Carbohydrate", "Fat","Protein"};
        healthReportView.createPieChart(nutritionPieChart,yNutritionData,xNutritionData);
    }

    @Override
    public void attachView(HealthReportView view) {
        healthReportView=view;
    }

    @Override
    public void detachView() {
        healthReportView=null;
    }

}

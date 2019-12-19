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
    public void getDailySummaryData() {
        RealmResults<DailySummary> realmResults = realmService.getCurrentDate();
        healthReportView.getDailySummary(realmResults.get(0));
        float[] yEnergyData= {realmResults.get(0).getEatenCalories(),realmResults.get(0).getBurnedCalories()};
        String[] xEnergyData = {"Energy Eaten", "Energy Burned"};
        if (yEnergyData[0] == 0 && yEnergyData[1] == 0) {
            healthReportView.invisiblePiechart();
        } else {
            healthReportView.createPieChart(energyPieChart, yEnergyData, xEnergyData);
            float[] yNutritionData = {realmResults.get(0).getEatenCarbs(), realmResults.get(0).getEatenFat(), realmResults.get(0).getEatenProtein()};
            String[] xNutritionData = {"Carbohydrate", "Fat", "Protein"};
            healthReportView.createPieChart(nutritionPieChart, yNutritionData, xNutritionData);
        }
    }


    @Override
    public ArrayList<Long> getMonthlyBurnedEnergy(int numberOfDaysInCurrentMonth) {
        RealmResults<DailySummary> realmResults = realmService.getCurrentMonth();
        ArrayList<Long> burnedEnergyInMonth = new ArrayList<>();
        for (int i = 1; i <= numberOfDaysInCurrentMonth; i++) {
            burnedEnergyInMonth.add((long) 0);
        }
        for (int i = 0; i < realmResults.size(); i++) {
            try {
                burnedEnergyInMonth.set(Integer.parseInt(realmResults.get(i).getDate()) - 1, realmResults.get(i).getBurnedCalories());
            } catch (Exception ignored) {
            }
        }
        return burnedEnergyInMonth;
    }

    @Override
    public ArrayList<Long> getMonthlyEatenEnergy(int numberOfDaysInCurrentMonth) {
        RealmResults<DailySummary> realmResults = realmService.getCurrentMonth();
        ArrayList<Long> eatenEnergyInMonth = new ArrayList<>();
        for (int i = 1; i <= numberOfDaysInCurrentMonth; i++) {
            eatenEnergyInMonth.add((long) 0);
        }
        for (int i = 0; i < realmResults.size(); i++) {
            try {
                eatenEnergyInMonth.set(Integer.parseInt(realmResults.get(i).getDate()) - 1, realmResults.get(i).getEatenCalories());
            } catch (Exception ignored) {
            }
        }
        return eatenEnergyInMonth;
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

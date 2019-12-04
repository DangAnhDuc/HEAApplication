package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.activity.HealthReportView;

public interface HealthReportPresenter extends BasePresenter<HealthReportView> {
    void getEnergyDataset();

    void getNutritionDataset();
}

package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.activity.HealthReportView;

import java.util.ArrayList;

public interface HealthReportPresenter extends BasePresenter<HealthReportView> {
    void getDailySummaryData();

    ArrayList<Long> getMonthlyEatenEnergy(int numberOfDaysInCurrentMonth);

    ArrayList<Long> getMonthlyBurnedEnergy(int numberOfDaysInCurrentMonth);

}

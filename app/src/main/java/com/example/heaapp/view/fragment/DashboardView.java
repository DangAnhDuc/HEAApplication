package com.example.heaapp.view.fragment;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.user_information.DailySummary;

public interface DashboardView extends BaseView {
    void dispayDailySummary(DailySummary dailySummary);
    void updateWaterAmount(String waterAmount);
}
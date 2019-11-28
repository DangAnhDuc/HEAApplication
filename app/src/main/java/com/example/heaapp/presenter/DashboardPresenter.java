package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.fragment.DashboardView;

public interface DashboardPresenter extends BasePresenter<DashboardView> {
    void getDailySummary();
    void addDrunkWater(String wateramount);
    void getUserInfoStatus();
    void getCurrentUserIndices();
    double calculateBurnedEnergy(double MET, String timePeriod);
}

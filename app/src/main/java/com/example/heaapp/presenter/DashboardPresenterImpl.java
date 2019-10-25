package com.example.heaapp.presenter;

import com.example.heaapp.view.fragment.DashboardView;

public class DashboardPresenterImpl implements DashboardPresenter {

    DashboardView dashboardView;
    int totalWaterAmount;


    @Override
    public void attachView(DashboardView view) {
        dashboardView=view;
    }

    @Override
    public void detachView() {
        dashboardView=null;
    }


    @Override
    public void addDrunkWater(int waterAmount) {
        totalWaterAmount= totalWaterAmount+waterAmount;
        dashboardView.updateWaterAmount(waterAmount);
    }
}

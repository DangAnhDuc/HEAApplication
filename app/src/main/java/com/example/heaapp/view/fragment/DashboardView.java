package com.example.heaapp.view.fragment;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.user_information.CurrentUserIndices;
import com.example.heaapp.model.user_information.DailySummary;

public interface DashboardView extends BaseView {
    void displayDailySummary(DailySummary dailySummary);
    void updateWaterAmount(String waterAmount);
    void isUserInfoEntered(boolean isEntered);
    void displayCurrentUserIndices(CurrentUserIndices currentUserIndices);
}

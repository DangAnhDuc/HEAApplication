package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.fragment.DashboardView;

public interface DashboardPresenter extends BasePresenter<DashboardView> {
    void getDailySummary();
    void addDrunkWater(long wateramount);
    void getUserInfoStatus();
    void getCurrentUserIndices();
}

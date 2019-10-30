package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.view.fragment.DashboardView;

import io.realm.RealmResults;

public interface DashboardPresenter extends BasePresenter<DashboardView> {
    void getDailySummary();
    void addDrunkWater(long wateramount);
    RealmResults<DailySummary> getCurrentDate();
}

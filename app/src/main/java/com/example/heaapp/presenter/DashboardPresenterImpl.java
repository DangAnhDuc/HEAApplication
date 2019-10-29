package com.example.heaapp.presenter;

import android.util.Log;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.view.fragment.DashboardView;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

public class DashboardPresenterImpl implements DashboardPresenter, OnTransactionCallback {
    DashboardView dashboardView;
    long totalWaterAmount;
    private final RealmService mRealmService;
    Realm realm= Realm.getDefaultInstance();

    public DashboardPresenterImpl(DashboardView dashboardView, RealmService mRealmService) {
        this.dashboardView = dashboardView;
        this.mRealmService = mRealmService;
    }

    @Override
    public void attachView(DashboardView view) {
        dashboardView=view;
    }

    @Override
    public void detachView() {
        dashboardView=null;
    }


    @Override
    public void getDailySummary() {
        RealmResults<DailySummary> result= realm.where(DailySummary.class)
                .equalTo("id",0)
                .findAll();
        dashboardView.dispayDailySummary(result.get(0));
    }

    @Override
    public void addDrunkWater(long waterAmount) {
        RealmResults<DailySummary> result= realm.where(DailySummary.class)
                .equalTo("id",0)
                .findAll();
        totalWaterAmount=result.get(0).getWaterConsume()+waterAmount;
        if(result==null){
            mRealmService.addWaterAsync(0,0,Calendar.getInstance().getTime(),totalWaterAmount,this);
        }
        else {
            mRealmService.modiWaterAsync(totalWaterAmount,this);
        }
    }


    @Override
    public void onTransactionSuccess() {
        RealmResults<DailySummary> result= realm.where(DailySummary.class)
                .equalTo("id",0)
                .findAll();
        dashboardView.updateWaterAmount(Long.toString(result.get(0).getWaterConsume()));
    }

    @Override
    public void onTransactionError(Exception e) {

    }
}

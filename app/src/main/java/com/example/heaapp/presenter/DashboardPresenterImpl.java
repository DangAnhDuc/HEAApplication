package com.example.heaapp.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.ultis.ultis;
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


    //get all information of each day
    @Override
    public void getDailySummary() {
        RealmResults<DailySummary> realmResults=getCurrentDate();
        dashboardView.dispayDailySummary(realmResults.get(0));
    }

    @Override
    public void addDrunkWater(long waterAmount) {
        RealmResults<DailySummary> realmResults=getCurrentDate();
        totalWaterAmount=realmResults.get(0).getWaterConsume();
        totalWaterAmount=totalWaterAmount+waterAmount;
        mRealmService.modifyWaterAsync(totalWaterAmount,this);
    }

    @Override
    public void getUserInfoStatus() {

    }

    @Override
    public RealmResults<DailySummary> getCurrentDate() {
        RealmResults<DailySummary> result= realm.where(DailySummary.class)
                .equalTo("date", Common.today)
                .findAll();
        return  result;
    }


    @Override
    public void onTransactionSuccess() {
        RealmResults<DailySummary> realmResults=getCurrentDate();
        dashboardView.updateWaterAmount(Long.toString(realmResults.get(0).getWaterConsume()));
    }

    @Override
    public void onTransactionError(Exception e) {

    }


}

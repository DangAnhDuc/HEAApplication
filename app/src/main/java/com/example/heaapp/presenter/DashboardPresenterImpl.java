package com.example.heaapp.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.CurrentUserIndices;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.view.fragment.DashboardView;

import io.realm.Realm;
import io.realm.RealmResults;

public class DashboardPresenterImpl implements DashboardPresenter, OnTransactionCallback {
    private DashboardView dashboardView;
    private Context context;
    private final RealmService mRealmService;
    private Realm realm= Realm.getDefaultInstance();

    public DashboardPresenterImpl(Context context,DashboardView dashboardView, RealmService mRealmService) {
        this.dashboardView = dashboardView;
        this.mRealmService = mRealmService;
        this.context=context;
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
        RealmResults<DailySummary> realmResults=mRealmService.getCurrentDate();
        dashboardView.displayDailySummary(realmResults.get(0));
    }

    @Override
    public void addDrunkWater(String waterAmount) {
        long drunkAmount=0;
        try {
            drunkAmount=Long.parseLong(waterAmount);
        }
        catch (Exception e){
            dashboardView.addDrunkWaterFailed();
        }
        RealmResults<DailySummary> realmResults=mRealmService.getCurrentDate();
        long totalWaterAmount = realmResults.get(0).getWaterConsume();
        totalWaterAmount = totalWaterAmount +drunkAmount;
        mRealmService.modifyWaterAsync(totalWaterAmount,this);
    }

    @Override
    public void getUserInfoStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        dashboardView.isUserInfoEntered(sharedPreferences.getBoolean("isEntered", false));
    }

    @Override
    public void getCurrentUserIndices() {
        RealmResults<CurrentUserIndices> realmResults = realm.where(CurrentUserIndices.class)
                .equalTo("id", 0)
                .findAll();
        dashboardView.displayCurrentUserIndices(realmResults.get(0));
    }

    @Override
    public void onTransactionSuccess() {
        RealmResults<DailySummary> realmResults=mRealmService.getCurrentDate();
        dashboardView.addDrunkWaterSuccessfully(Long.toString(realmResults.get(0).getWaterConsume()));
    }

    @Override
    public void onTransactionError(Exception e) {
        dashboardView.addDrunkWaterFailed();
    }


}

package com.example.heaapp.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.CurrentUserIndices;
import com.example.heaapp.model.user_information.CurrentUserInfo;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.view.fragment.DashboardView;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

public class DashboardPresenterImpl implements DashboardPresenter, OnTransactionCallback {
    private DashboardView dashboardView;
    private Context context;
    private final RealmService realmService;
    private Realm realm = Realm.getDefaultInstance();

    public DashboardPresenterImpl(Context context, DashboardView dashboardView, RealmService mRealmService) {
        this.dashboardView = dashboardView;
        this.realmService = mRealmService;
        this.context = context;
    }

    @Override
    public void attachView(DashboardView view) {
        dashboardView = view;
    }

    @Override
    public void detachView() {
        dashboardView = null;
    }


    //get all information of each day
    @Override
    public void getDailySummary() {
        RealmResults<DailySummary> realmResults = realmService.getCurrentDate();
        dashboardView.displayDailySummary(realmResults.get(0));
    }

    @Override
    public void addDrunkWater(String waterAmount) {
        long drunkAmount = 0;
        try {
            drunkAmount = Long.parseLong(waterAmount);
        } catch (Exception e) {
            dashboardView.addDrunkWaterFailed();
        }
        RealmResults<DailySummary> realmResults = realmService.getCurrentDate();
        long totalWaterAmount = Objects.requireNonNull(realmResults.get(0)).getWaterConsume();
        totalWaterAmount = totalWaterAmount + drunkAmount;
        realmService.modifyWaterAsync(totalWaterAmount, this);
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
        RealmResults<DailySummary> realmResults = realmService.getCurrentDate();
        assert realmResults.get(0) != null;
        dashboardView.addDrunkWaterSuccessfully(Long.toString(Objects.requireNonNull(realmResults.get(0)).getWaterConsume()));
    }

    @Override
    public void onTransactionError(Exception e) {
        dashboardView.addDrunkWaterFailed();
    }

    @Override
    public double calculateBurnedEnergy(double MET, String timePeriod) {
        RealmResults<CurrentUserInfo> realmResults = realmService.getCurrentUser();
        CurrentUserInfo currentUserInfo = realmResults.get(0);
        return ((MET * currentUserInfo.getWeight() * 3.5) / 200) * (Integer.parseInt(timePeriod));
    }

    @Override
    public void addActivity(String activityName, String time, long burnedEnergy) {
        double totalEnergyBurned = Objects.requireNonNull(realmService.getCurrentDate().get(0)).getBurnedCalories();
        double totalneededCalories = Objects.requireNonNull(realmService.getCurrentDate().get(0)).getNeededCalories();

        totalneededCalories = totalneededCalories + burnedEnergy;
        totalEnergyBurned = totalEnergyBurned + burnedEnergy;
        realmService.modifyBurnedEnergyAsync((Double.valueOf(totalEnergyBurned)).longValue(), (Double.valueOf(totalneededCalories)).longValue(), new OnTransactionCallback() {
            @Override
            public void onTransactionSuccess() {
                realmService.addActivities(activityName, time, burnedEnergy);
                dashboardView.addActivitiesSuccess();
            }

            @Override
            public void onTransactionError(Exception e) {
                dashboardView.addActivitiesFailed();

            }
        });
    }
}

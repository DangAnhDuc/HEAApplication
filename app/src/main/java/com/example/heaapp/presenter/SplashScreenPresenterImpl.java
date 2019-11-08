package com.example.heaapp.presenter;

import android.util.Log;

import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.FoodApiServices;
import com.example.heaapp.model.food.Data;
import com.example.heaapp.model.food.FoodInfor;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.view.activity.SpashScreenView;
import com.google.android.exoplayer2.C;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class SplashScreenPresenterImpl implements SplashScreenPresenter {

    private RealmService realmService;
    private SpashScreenView spashScreenView;
    private static AtomicLong dailySummaryPrimaryKey;
    private Realm realm = Realm.getDefaultInstance();
    private CompositeDisposable compositeDisposable=new CompositeDisposable();

    public SplashScreenPresenterImpl(RealmService realmService, SpashScreenView spashScreenView) {
        this.realmService = realmService;
        this.spashScreenView = spashScreenView;
    }

    @Override
    public void attachView(SpashScreenView view) {
        spashScreenView = view;
    }

    @Override
    public void detachView() {
        spashScreenView = null;
    }

    @Override
    public void firstTimeInit() {
        try {
            dailySummaryPrimaryKey = new AtomicLong(realm.where(DailySummary.class).max("id").longValue() + 1);
        } catch (Exception e) {
            realmService.initDatabaseTable();
            dailySummaryPrimaryKey = new AtomicLong(realm.where(DailySummary.class).findAll().size());
            RealmResults<DailySummary> realmResults = realm.where(DailySummary.class).equalTo("id", 0).findAll();
            realmResults.deleteAllFromRealm();

        }
    }

    @Override
    public void getFoodList(){
        FoodApiServices foodApiServices = ApiUtils.getFoodApiServices();
        Disposable disposableFood;
        for (int idFood = 1000; idFood <= 1100; idFood++) {
            disposableFood = foodApiServices.getFoods(idFood)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError, this::handleSuccess);
            compositeDisposable.add(disposableFood);
        }
    }
    private void handleSuccess() {

    }

    private void handleError(Throwable throwable) {

    }

    private void handleResponse(FoodInfor foodInfor) {
        Common.foodList.add(foodInfor.getData());
    }

    @Override
    public void dailyInit() {
        //Daily check
        RealmResults<DailySummary> realmResults = realm.where(DailySummary.class).equalTo("date", Common.today).findAll();
        if (realmResults.size() == 0) {
            realm.beginTransaction();
            DailySummary dailySummary = realm.createObject(DailySummary.class);
            dailySummary.setId(dailySummaryPrimaryKey.getAndIncrement());
            dailySummary.setDate(Common.today);
            dailySummary.setWaterConsume(0);
            dailySummary.setEatenCalories(0);
            dailySummary.setBurnedCalories(0);
            realm.commitTransaction();
        }
    }

}

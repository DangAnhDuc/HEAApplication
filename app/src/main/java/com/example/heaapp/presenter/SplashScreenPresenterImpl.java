package com.example.heaapp.presenter;

import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.view.activity.SpashScreenView;

import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmResults;

public class SplashScreenPresenterImpl implements SplashScreenPresenter {

    private RealmService realmService;
    private SpashScreenView spashScreenView;
    private static AtomicLong dailySummaryPrimaryKey;
    private Realm realm = Realm.getDefaultInstance();

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

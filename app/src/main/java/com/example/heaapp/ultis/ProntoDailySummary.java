package com.example.heaapp.ultis;

import android.app.Application;

import com.example.heaapp.model.user_information.CurrentUserInfo;
import com.example.heaapp.model.user_information.DailySummary;

import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ProntoDailySummary extends Application {
    public static AtomicLong currentUserInfoPrimaryKey;
    public static AtomicLong dailySummaryPrimaryKey;

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration configuration= new RealmConfiguration.Builder()
                .name("heaapp.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
        Realm realm = Realm.getInstance(configuration);

        try {
            dailySummaryPrimaryKey=new AtomicLong(realm.where(DailySummary.class).max("id").longValue()+1);
        }
        catch (Exception e){
            realm.beginTransaction();
            DailySummary dailySummary=realm.createObject(DailySummary.class,0);
            dailySummaryPrimaryKey=new AtomicLong(realm.where(DailySummary.class).max("id").longValue()+1);
            RealmResults<DailySummary> realmResults=realm.where(DailySummary.class).equalTo("id",0).findAll();
            realmResults.deleteAllFromRealm();
            realm.commitTransaction();
        }

        try {
            currentUserInfoPrimaryKey=new AtomicLong(realm.where(CurrentUserInfo.class).max("id").longValue()+1);
        }
        catch (Exception e){
            realm.beginTransaction();
            CurrentUserInfo currentUserInfo=realm.createObject(CurrentUserInfo.class,0);
            currentUserInfoPrimaryKey=new AtomicLong(realm.where(CurrentUserInfo.class).max("id").longValue()+1);
            RealmResults<CurrentUserInfo> realmResults=realm.where(CurrentUserInfo.class).equalTo("id",0).findAll();
            realmResults.deleteAllFromRealm();
            realm.commitTransaction();
        }
    }
}

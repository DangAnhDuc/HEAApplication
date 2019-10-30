package com.example.heaapp.service;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.ultis.Common;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.Realm.Transaction;
import io.realm.Realm.Transaction.Callback;
import io.realm.RealmResults;

public class RealmService {
    private final Realm mRealm;
    private static RealmService instance;
    public RealmService(Realm realm) {
        mRealm = realm;
    }

    public static RealmService getInstance() {
        if(instance == null) {
            instance = new RealmService( Realm.getDefaultInstance());
        }
        return instance;
    }
    public void closeRealm() {
        mRealm.close();
    }

    public RealmResults<DailySummary> getAllDaySummary() {
        return mRealm.where(DailySummary.class).findAll();
    }

    //modify drunk water
    public void modifyWaterAsync(long totalWaterAmount,OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(new Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DailySummary> result = realm.where(DailySummary.class)
                        .equalTo("date", Common.today)
                        .findAll();
                result.setValue("waterConsume", totalWaterAmount);
            }
        }, new Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (onTransactionCallback != null) {
                    onTransactionCallback.onTransactionSuccess();
                }
            }
        }, new Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if(onTransactionCallback!=null){
                    onTransactionCallback.onTransactionError((Exception) error);
                }
            }
        });
    }
}

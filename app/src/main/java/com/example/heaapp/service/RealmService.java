package com.example.heaapp.service;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.CurrentUserIndices;
import com.example.heaapp.model.user_information.CurrentUserInfo;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.ultis.Common;

import io.realm.Realm;
import io.realm.Realm.Transaction;
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

    public RealmResults<CurrentUserInfo> getCurrentUser() {
        RealmResults<CurrentUserInfo> resultCurrentUser= mRealm.where(CurrentUserInfo.class)
                .equalTo("id",0)
                .findAll();
        return  resultCurrentUser;
    }

    public RealmResults<DailySummary> getCurrentDate() {
        RealmResults<DailySummary> resultCurrentDate= mRealm.where(DailySummary.class)
                .equalTo("date", Common.today)
                .findAll();
        return  resultCurrentDate;
    }

    //modify drunk water
    public void modifyWaterAsync(long totalWaterAmount,OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(new Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DailySummary> resultCurrentDate= realm.where(DailySummary.class)
                        .equalTo("date", Common.today)
                        .findAll();
                resultCurrentDate.setValue("waterConsume", totalWaterAmount);
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

    //modify user information
    public void modifyUserInforAsync(int age,String sex,long weight, long height,long waist, long hip, long chest, OnTransactionCallback onTransactionCallback){
        mRealm.executeTransactionAsync(new Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<CurrentUserInfo> resultCurrentUser= realm.where(CurrentUserInfo.class)
                        .equalTo("id",0)
                        .findAll();
                resultCurrentUser.setValue("age", age);
                resultCurrentUser.setValue("sex", sex);
                resultCurrentUser.setValue("weight", weight);
                resultCurrentUser.setValue("height", height);
                resultCurrentUser.setValue("waist", waist);
                resultCurrentUser.setValue("hip", hip);
                resultCurrentUser.setValue("chest", chest);
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

    //add user indices
    public void addUserIndices(long id,double BMI,double bodyMass,double bodyWater, double waterRequired,double bloodVolume,double bodyFat,
                               double FFMI,double dailyCal,OnTransactionCallback onTransactionCallback ){
        mRealm.executeTransactionAsync(new Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<CurrentUserIndices> realmResults = realm.where(CurrentUserIndices.class)
                        .equalTo("id", 0)
                        .findAll();
                realmResults.setValue("id", id);
                realmResults.setValue("BMI", BMI);
                realmResults.setValue("bodyMass", bodyMass);
                realmResults.setValue("bodyWater", bodyWater);
                realmResults.setValue("waterRequired", waterRequired);
                realmResults.setValue("bloodVolume", bloodVolume);
                realmResults.setValue("bodyFat", bodyFat);
                realmResults.setValue("FFMI", FFMI);
                realmResults.setValue("dailyCal", dailyCal);
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

package com.example.heaapp.service;

import android.util.Log;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.food.Data;
import com.example.heaapp.model.food.Dishes;
import com.example.heaapp.model.user_information.CurrentUserIndices;
import com.example.heaapp.model.user_information.CurrentUserInfo;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.presenter.FoodAddingPresenterImpl;
import com.example.heaapp.ultis.Common;

import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.Realm.Transaction;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmService {
    private final Realm mRealm;
    private static RealmService instance;

    public RealmService(Realm realm) {
        mRealm = realm;
    }

    public static RealmService getInstance() {
        if (instance == null) {
            instance = new RealmService(Realm.getDefaultInstance());
        }
        return instance;
    }

    public void closeRealm() {
        mRealm.close();
    }

    public RealmResults<DailySummary> getAllDaySummary() {
        return mRealm.where(DailySummary.class).findAll();
    }

    public RealmResults<Data> getAllFoodData() {
        return mRealm.where(Data.class).findAll();
    }

    public RealmResults<CurrentUserInfo> getCurrentUser() {
        return mRealm.where(CurrentUserInfo.class)
                .equalTo("id", 0)
                .findAll();
    }

    public RealmResults<DailySummary> getCurrentDate() {
        return mRealm.where(DailySummary.class)
                .equalTo("date", Common.today)
                .findAll();
    }

    //modify drunk water
    public void modifyWaterAsync(long totalWaterAmount, OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
            RealmResults<DailySummary> resultCurrentDate = realm.where(DailySummary.class)
                    .equalTo("date", Common.today)
                    .findAll();
            resultCurrentDate.setValue("waterConsume", totalWaterAmount);
        }, () -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionSuccess();
            }
        }, error -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionError((Exception) error);
            }
        });
    }

    //modify user information
    public void modifyUserInfoAsync(int age, String sex, long weight, long height, long waist, long hip, long chest, OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
            RealmResults<CurrentUserInfo> resultCurrentUser = realm.where(CurrentUserInfo.class)
                    .equalTo("id", 0)
                    .findAll();
            resultCurrentUser.setValue("age", age);
            resultCurrentUser.setValue("sex", sex);
            resultCurrentUser.setValue("weight", weight);
            resultCurrentUser.setValue("height", height);
            resultCurrentUser.setValue("waist", waist);
            resultCurrentUser.setValue("hip", hip);
            resultCurrentUser.setValue("chest", chest);
        }, () -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionSuccess();
            }
        }, error -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionError((Exception) error);
            }
        });
    }


    //modify burned energy
    public void modifyBurnedEnergyAsync(long totalBurnedEnergy, long totalneededCalories, OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
            RealmResults<DailySummary> resultCurrentDate = realm.where(DailySummary.class)
                    .equalTo("date", Common.today)
                    .findAll();
            resultCurrentDate.setValue("burnedCalories", totalBurnedEnergy);
            resultCurrentDate.setValue("neededCalories", totalneededCalories);

        }, () -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionSuccess();
            }
        }, error -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionError((Exception) error);
            }
        });
    }

    //modify needed energy
    public void modifyNeededEnergyAsync(long neededCalories, OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
            RealmResults<DailySummary> resultCurrentDate = realm.where(DailySummary.class)
                    .equalTo("date", Common.today)
                    .findAll();
            resultCurrentDate.setValue("neededCalories", neededCalories);
        }, () -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionSuccess();
            }
        }, error -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionError((Exception) error);
            }
        });
    }

    //update nutrition
    public void updateNutrition(long neededCalories, long totalEnergy, long totalCarbs, long totalProtein, long totalFat,
                                String foodTime, String name, long energy, long carbs, long protein, long fat, OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
            RealmResults<DailySummary> resultCurrentDate = realm.where(DailySummary.class)
                    .equalTo("date", Common.today)
                    .findAll();

            Dishes dishes = realm.createObject(Dishes.class);
            dishes.setName(name);
            dishes.setEnergy(energy);
            dishes.setCarbs(carbs);
            dishes.setProtein(protein);
            dishes.setFat(fat);
            switch (foodTime) {
                case "Breakfast":
                    resultCurrentDate.get(0).getBreakfastDishes().add(dishes);
                    break;
                case "Launch":
                    resultCurrentDate.get(0).getLaunchDishes().add(dishes);
                    break;
                case "Dinner":
                    resultCurrentDate.get(0).getDinnerDishes().add(dishes);
                    break;
            }
            realm.copyToRealmOrUpdate(resultCurrentDate);


            resultCurrentDate.setValue("neededCalories", neededCalories);
            resultCurrentDate.setValue("eatenCalories", totalEnergy);
            resultCurrentDate.setValue("eatenCarbs", totalCarbs);
            resultCurrentDate.setValue("eatenProtein", totalProtein);
            resultCurrentDate.setValue("eatenFat", totalFat);

        }, () -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionSuccess();
            }
        }, error -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionError((Exception) error);
            }
        });
    }

    public void updateBreakfastDishes(RealmList<Dishes> dishes, OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
            RealmResults<DailySummary> resultCurrentDate = realm.where(DailySummary.class)
                    .equalTo("date", Common.today)
                    .findAll();
            resultCurrentDate.setValue("breakfastDishes", dishes);
        }, () -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionSuccess();
            }
        }, error -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionError((Exception) error);
            }
        });
    }

    public void updateLaunchDishes(RealmList<Dishes> dishes, OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
            RealmResults<DailySummary> resultCurrentDate = realm.where(DailySummary.class)
                    .equalTo("date", Common.today)
                    .findAll();
            resultCurrentDate.setValue("launchDishes", dishes);
        }, () -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionSuccess();
            }
        }, error -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionError((Exception) error);
            }
        });
    }


    public void updateDinnerDishes(RealmList<Dishes> dishes, OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
            RealmResults<DailySummary> resultCurrentDate = realm.where(DailySummary.class)
                    .equalTo("date", Common.today)
                    .findAll();
            resultCurrentDate.setValue("dinnerDishes", dishes);
        }, () -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionSuccess();
            }
        }, error -> {
            if (onTransactionCallback != null) {
                onTransactionCallback.onTransactionError((Exception) error);
            }
        });
    }

    //add user indices
    public void addUserIndices(long id, double BMI, double bodyMass, double bodyWater, double waterRequired, double bloodVolume, double bodyFat,
                               double FFMI, double dailyCal, OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
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
                if (onTransactionCallback != null) {
                    onTransactionCallback.onTransactionError((Exception) error);
                }
            }
        });
    }

    //init database table
    public void initDatabaseTable(OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
            //create database table for daily summary
            DailySummary dailySummary = realm.createObject(DailySummary.class);
            dailySummary.setId(0);
            dailySummary.setDate(Common.today);
            dailySummary.setWaterConsume(0);
            dailySummary.setEatenCalories(0);
            dailySummary.setBurnedCalories(0);
            dailySummary.setNeededCalories(0);
            dailySummary.setEatenCarbs(0);
            dailySummary.setEatenProtein(0);
            dailySummary.setEatenFat(0);

            //create database table for current user
            CurrentUserInfo currentUserInfo = realm.createObject(CurrentUserInfo.class);
            currentUserInfo.setId(0);
            currentUserInfo.setAge(0);
            currentUserInfo.setSex("Male");
            currentUserInfo.setWeight(0);
            currentUserInfo.setHeight(0);
            currentUserInfo.setWaist(0);
            currentUserInfo.setHip(0);
            currentUserInfo.setChest(0);

            //create table for current user indices
            CurrentUserIndices currentUserIndices = realm.createObject(CurrentUserIndices.class);
            currentUserIndices.setId(0);
            currentUserIndices.setBMI(0);
            currentUserIndices.setBodyMass(0);
            currentUserIndices.setBodyWater(0);
            currentUserIndices.setWaterRequired(0);
            currentUserIndices.setBloodVolume(0);
            currentUserIndices.setBodyFat(0);
            currentUserIndices.setFFMI(0);
            currentUserIndices.setDailyCal(0);
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
                if (onTransactionCallback != null) {
                    onTransactionCallback.onTransactionError((Exception) error);
                }
            }
        });
    }


//    public void addFoodTable(Data data){
//        mRealm.executeTransactionAsync(realm -> {
//            Data foodData = realm.createObject(Data.class);
//            foodData.setId(data.getId());
//            foodData.setCountry(data.getCountry());
//            foodData.setBarcode(data.getBarcode());
//            foodData.setNameTranslations(realm.copyToRealm(data.getNameTranslations()));
//            foodData.setDisplayNameTranslations(realm.copyToRealm(data.getDisplayNameTranslations()));
//            foodData.setIngredientsTranslations(realm.copyToRealm(data.getIngredientsTranslations()));
//            foodData.setOriginTranslations(realm.copyToRealm(data.getOriginTranslations()));
//            foodData.setStatus(data.getStatus());
//            foodData.setQuantity(data.getQuantity());
//            foodData.setUnit(data.getUnit());
//            foodData.setPortionQuantity(data.getPortionQuantity());
//            foodData.setPortionUnit(data.getPortionUnit());
//            foodData.setAlcoholByVolume(data.getAlcoholByVolume());
//            foodData.setImages(data.getImages());
//            foodData.setNutrients(data.getNutrients());
//            foodData.setCreatedAt(data.getCreatedAt());
//            foodData.setUpdatedAt(data.getUpdatedAt());
//
//        }, new Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                Log.d("succes", "onSuccess: "+data.getId());
//            }
//
//    });
//    }
}

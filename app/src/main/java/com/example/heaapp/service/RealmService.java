package com.example.heaapp.service;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.food.Dishes;
import com.example.heaapp.model.reminder.TimeReminder;
import com.example.heaapp.model.user_information.CurrentUserDetail;
import com.example.heaapp.model.user_information.CurrentUserIndices;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.model.user_information.User;
import com.example.heaapp.model.workout.Activities;
import com.example.heaapp.ultis.Common;

import io.realm.Realm;
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

    public RealmResults<CurrentUserDetail> getCurrentUser() {
        return mRealm.where(CurrentUserDetail.class)
                .equalTo("id", 0)
                .findAll();
    }

    public RealmResults<TimeReminder> getReminder() {
        return mRealm.where(TimeReminder.class)
                     .findAll();
    }
  
    public RealmResults<User> getUser(){
        return mRealm.where(User.class)
                .equalTo("userId","0")
                .findAll();
    }

    public RealmResults<DailySummary> getCurrentDate() {
        return mRealm.where(DailySummary.class)
                .equalTo("date", Common.currentDate)
                .equalTo("month", Common.currentMonth)
                .equalTo("year", Common.currentYear)
                .findAll();
    }

    public RealmResults<DailySummary> getCurrentMonth() {
        return mRealm.where(DailySummary.class)
                .equalTo("month", Common.currentMonth)
                .equalTo("year", Common.currentYear)
                .findAll();
    }
    //modify drunk water
    public void modifyWaterAsync(long totalWaterAmount, OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
            RealmResults<DailySummary> resultCurrentDate = realm.where(DailySummary.class)
                    .equalTo("date", Common.currentDate)
                    .equalTo("month", Common.currentMonth)
                    .equalTo("year", Common.currentYear)
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
            RealmResults<CurrentUserDetail> resultCurrentUser = realm.where(CurrentUserDetail.class)
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
                    .equalTo("date", Common.currentDate)
                    .equalTo("month", Common.currentMonth)
                    .equalTo("year", Common.currentYear)
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
                    .equalTo("date", Common.currentDate)
                    .equalTo("month", Common.currentMonth)
                    .equalTo("year", Common.currentYear)
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
                    .equalTo("date", Common.currentDate)
                    .equalTo("month", Common.currentMonth)
                    .equalTo("year", Common.currentYear)
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

    public void addActivities(String name, String time, long burnedEnergy) {
        mRealm.executeTransactionAsync(realm -> {
            RealmResults<DailySummary> resultCurrentDate = realm.where(DailySummary.class)
                    .equalTo("date", Common.currentDate)
                    .equalTo("month", Common.currentMonth)
                    .equalTo("year", Common.currentYear)
                    .findAll();

            Activities activities = realm.createObject(Activities.class);
            activities.setName(name);
            activities.setTime(time);
            activities.setEnergy(burnedEnergy);
            resultCurrentDate.get(0).getActivities().add(activities);
            realm.copyToRealmOrUpdate(resultCurrentDate);
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

    public void addReminder(int hour, int min, RealmList<String> listDay, OnTransactionCallback onTransactionCallback){
        mRealm.executeTransactionAsync(realm -> {
            TimeReminder timeReminder = realm.createObject(TimeReminder.class);
            timeReminder.setHour(hour);
            timeReminder.setMinute(min);
            timeReminder.setDayList(listDay);
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

    public void removeReminder(int pos){
        mRealm.executeTransactionAsync(realm -> {
            RealmResults<TimeReminder> realmResults = realm.where(TimeReminder.class)
                    .findAll();
            realmResults.deleteFromRealm(pos);
        });
    }
    //init database table
    public void initDatabaseTable(OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
            //create database table for daily summary
            DailySummary dailySummary = realm.createObject(DailySummary.class);
            dailySummary.setId(0);
            dailySummary.setDate(Common.currentDate);
            dailySummary.setMonth(Common.currentMonth);
            dailySummary.setYear(Common.currentYear);
            dailySummary.setWaterConsume(0);
            dailySummary.setEatenCalories(0);
            dailySummary.setBurnedCalories(0);
            dailySummary.setNeededCalories(0);
            dailySummary.setEatenCarbs(0);
            dailySummary.setEatenProtein(0);
            dailySummary.setEatenFat(0);

            //create database table for current user
            CurrentUserDetail currentUserDetail = realm.createObject(CurrentUserDetail.class);
            currentUserDetail.setId(0);
            currentUserDetail.setAge(0);
            currentUserDetail.setSex("Male");
            currentUserDetail.setWeight(0);
            currentUserDetail.setHeight(0);
            currentUserDetail.setWaist(0);
            currentUserDetail.setHip(0);
            currentUserDetail.setChest(0);

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

            //create table for user
            User user = realm.createObject(User.class);
            user.setUsername("Sample");
            user.setImageURl("default");
            user.setUserId(String.valueOf(0));
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

    public void updateUser(String name,String imageURL,OnTransactionCallback onTransactionCallback) {
        mRealm.executeTransactionAsync(realm -> {
            RealmResults<User> realmResults = realm.where(User.class)
                    .equalTo("userId", "0")
                    .findAll();
            realmResults.setValue("username",name);
            realmResults.setValue("imageURl",imageURL);
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
}

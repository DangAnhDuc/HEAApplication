package com.example.heaapp.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.FoodApiServices;
import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.food.Dishes;
import com.example.heaapp.model.food.FoodInfor;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.receiver.AlarmReceiver;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.view.activity.SpashScreenView;

import java.util.Calendar;
import java.util.Objects;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static android.content.Context.ALARM_SERVICE;

public class SplashScreenPresenterImpl implements SplashScreenPresenter, OnTransactionCallback {

    private RealmService realmService;
    private static AtomicLong dailySummaryPrimaryKey;
    private Realm realm = Realm.getDefaultInstance();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;

    public SplashScreenPresenterImpl(RealmService realmService, Context context) {
        this.realmService = realmService;
        this.context=context;
    }

    @Override
    public void attachView(SpashScreenView view) {
    }

    @Override
    public void detachView() {
    }

    @Override
    public void firstTimeInit() {
        try {
            dailySummaryPrimaryKey = new AtomicLong(Objects.requireNonNull(realm.where(DailySummary.class).max("id")).longValue() + 1);
            dailyInit();
        } catch (Exception e) {
            realmService.initDatabaseTable(this);
            dailySummaryPrimaryKey = new AtomicLong(realm.where(DailySummary.class).findAll().size());
            RealmResults<DailySummary> realmResults = realm.where(DailySummary.class).equalTo("id", 0).findAll();
            realmResults.deleteAllFromRealm();
            AlarmManager alarmManager= (AlarmManager) context.getSystemService(ALARM_SERVICE);
            Intent notificationIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 30);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, broadcast);
        }
    }

    @Override
    public void getFoodList() {
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

    @Override
    public void setlang() {
        Locale locale = new Locale(getShareFrefLang());
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    private String getShareFrefLang(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String sysLang = Locale.getDefault().getLanguage();
        String lang = sharedPreferences.getString("Lang", sysLang);
        return lang;
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
        double dailyCal;
        if (Objects.requireNonNull(realmService.getCurrentUser().get(0)).getSex().equals("Male")) {
            dailyCal = 66.4730 + (17.7516 * Objects.requireNonNull(realmService.getCurrentUser().get(0)).getWeight()) + (5.0033 * Objects.requireNonNull(realmService.getCurrentUser().get(0)).getHeight()) - (6.7550 * Objects.requireNonNull(realmService.getCurrentUser().get(0)).getAge());
        } else {
            dailyCal = 655.0955 + (9.5634 * Objects.requireNonNull(realmService.getCurrentUser().get(0)).getWeight()) + (1.8496 * Objects.requireNonNull(realmService.getCurrentUser().get(0)).getHeight()) - (4.6756 * Objects.requireNonNull(realmService.getCurrentUser().get(0)).getAge());
        }
        RealmResults<DailySummary> realmResults = realm.where(DailySummary.class).equalTo("date", Common.today).findAll();
        RealmList<Dishes> breakfastDishes = new RealmList<>();
        RealmList<Dishes> launchDishes = new RealmList<>();
        RealmList<Dishes> dinnerDishes = new RealmList<>();

        if (realmResults.size() == 0) {
            realm.beginTransaction();
            DailySummary dailySummary = realm.createObject(DailySummary.class);
            dailySummary.setId(dailySummaryPrimaryKey.getAndIncrement());
            dailySummary.setDate(Common.today);
            dailySummary.setWaterConsume(0);
            dailySummary.setEatenCalories(0);
            dailySummary.setBurnedCalories(0);
            dailySummary.setNeededCalories(Double.valueOf(dailyCal).longValue());
            dailySummary.setEatenCarbs(0);
            dailySummary.setEatenProtein(0);
            dailySummary.setEatenFat(0);
            dailySummary.setBreakfastDishes(breakfastDishes);
            dailySummary.setLaunchDishes(launchDishes);
            dailySummary.setDinnerDishes(dinnerDishes);
            realm.commitTransaction();
        }
    }

    @Override
    public void onTransactionSuccess() {
        dailyInit();
    }

    @Override
    public void onTransactionError(Exception e) {

    }
}

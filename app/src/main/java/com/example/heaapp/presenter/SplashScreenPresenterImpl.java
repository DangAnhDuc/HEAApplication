package com.example.heaapp.presenter;

import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.FoodApiServices;
import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.food.Dishes;
import com.example.heaapp.model.food.FoodInfor;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.view.activity.SpashScreenView;

import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SplashScreenPresenterImpl implements SplashScreenPresenter, OnTransactionCallback {

    private RealmService realmService;
    private SpashScreenView spashScreenView;
    private static AtomicLong dailySummaryPrimaryKey;
    private Realm realm = Realm.getDefaultInstance();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
            dailyInit();
        } catch (Exception e) {
            realmService.initDatabaseTable(this);
            dailySummaryPrimaryKey = new AtomicLong(realm.where(DailySummary.class).findAll().size());
            RealmResults<DailySummary> realmResults = realm.where(DailySummary.class).equalTo("id", 0).findAll();
            realmResults.deleteAllFromRealm();

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
        if (realmService.getCurrentUser().get(0).getSex().equals("Male")) {
            dailyCal = 66.4730 + (17.7516 * realmService.getCurrentUser().get(0).getWeight()) + (5.0033 * realmService.getCurrentUser().get(0).getHeight()) - (6.7550 * realmService.getCurrentUser().get(0).getAge());
        } else {
            dailyCal = 655.0955 + (9.5634 * realmService.getCurrentUser().get(0).getWeight()) + (1.8496 * realmService.getCurrentUser().get(0).getHeight()) - (4.6756 * realmService.getCurrentUser().get(0).getAge());
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

package com.example.heaapp.presenter;

import android.content.Context;

import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.FoodApiServices;
import com.example.heaapp.api.WeatherApiServices;
import com.example.heaapp.model.food.FoodInfor;
import com.example.heaapp.model.food.Information;
import com.example.heaapp.model.news.Article;
import com.example.heaapp.view.activity.FoodAddingView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FoodAddingPresenterImpl implements  FoodAddingPresenter{
    FoodAddingView foodAddingView;
    Context context;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();

    public FoodAddingPresenterImpl(FoodAddingView foodAddingView, Context context) {
        this.foodAddingView = foodAddingView;
        this.context = context;
    }

    @Override
    public void attachView(FoodAddingView view) {
        foodAddingView=view;
    }

    @Override
    public void detachView() {
        foodAddingView=null;
    }

    @Override
    public void crawlFoodData() {
        FoodApiServices foodApiServices= ApiUtils.getFoodApiServices();
        Disposable disposableFood= foodApiServices.getFoods()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError, this::handleSuccess);
        compositeDisposable.add(disposableFood);
    }

    @Override
    public void disposeApi() {
        compositeDisposable.dispose();
    }

    private void handleSuccess() {

    }

    private void handleError(Throwable throwable) {
        foodAddingView.crawlDataFailed("Error"+throwable.getLocalizedMessage());
    }

    private void handleResponse(FoodInfor foodInfor) {
        List<Information> informationList = foodInfor.getInformationList();
        foodAddingView.crawlDataSuccess(informationList);
    }
}

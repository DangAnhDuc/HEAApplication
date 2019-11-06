package com.example.heaapp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.FoodApiServices;
import com.example.heaapp.api.WeatherApiServices;
import com.example.heaapp.model.food.Data;
import com.example.heaapp.model.food.FoodInfor;
import com.example.heaapp.model.news.Article;
import com.example.heaapp.view.activity.FoodAddingView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FoodAddingPresenterImpl implements  FoodAddingPresenter{
    private FoodAddingView foodAddingView;
    private Context context;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private List<Data> foodList= new ArrayList<>();

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
        Disposable disposableFood;
        for(int idFood = 1000 ;idFood <= 1020; idFood++) {
            disposableFood=foodApiServices.getFoods(idFood)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError, this::handleSuccess);
            compositeDisposable.add(disposableFood);
        }
    }

    @Override
    public void disposeApi() {
        compositeDisposable.dispose();
    }

    private void handleSuccess() {
        Log.d("asd", "handleSuccess: ");

    }

    private void handleError(Throwable throwable) {
        foodAddingView.crawlDataFailed("Error"+throwable.getLocalizedMessage());
    }

    private void handleResponse(FoodInfor foodInfor) {
        foodList.add(foodInfor.getData());
        Log.d("adas", String.valueOf(foodList.size()));
        foodAddingView.crawlDataSuccess(foodList);
    }
}

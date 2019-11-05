package com.example.heaapp.presenter;

import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.NewsApiServices;
import com.example.heaapp.api.WeatherApiServices;
import com.example.heaapp.model.airweather.CityInfor;
import com.example.heaapp.model.news.Article;
import com.example.heaapp.model.news.News;
import com.example.heaapp.view.fragment.HealthInforView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HealthInfoPresenterImpl implements HealthInfoPresenter {

    private HealthInforView healthInforView;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();

    public HealthInfoPresenterImpl(HealthInforView healthInforView) {
        this.healthInforView = healthInforView;
    }

    //get latest news and weather data
    @Override
    public void getLatestData() {
        NewsApiServices newsApiServices= ApiUtils.getNewsApiService();
        Disposable disposableNews= newsApiServices.getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError, this::handleSuccess);

        WeatherApiServices weatherApiServices= ApiUtils.getWeatherApiService();
        Disposable disposableWeather= weatherApiServices.getHCMObs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError, this::handleSuccess);
        compositeDisposable.add(disposableNews);
        compositeDisposable.add(disposableWeather);
    }

    @Override
    public void disposeApi() {
        compositeDisposable.dispose();
    }


    private void handleResponse(CityInfor cityInfor) {
        healthInforView.getCityInfoSuccess(cityInfor);
    }

    private void handleSuccess() {
    }

    private void handleError(Throwable throwable) {
        healthInforView.getListNewsFailed("Error"+throwable.getLocalizedMessage());
    }

    private void handleResponse(News news) {
        List<Article> articles = news.getArticles();
        healthInforView.getListNewsSuccess(articles);
    }


    @Override
    public void attachView(HealthInforView view) {
        healthInforView=view;
    }

    @Override
    public void detachView() {
        healthInforView=null;
    }
}
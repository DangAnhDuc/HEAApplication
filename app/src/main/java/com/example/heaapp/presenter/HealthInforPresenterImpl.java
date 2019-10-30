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

public class HealthInforPresenterImpl implements HealthInforPresenter {

    private HealthInforView healthInforView;
    private List<Article> articles;
    private CityInfor cityInformation;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();

    public HealthInforPresenterImpl(HealthInforView healthInforView) {
        this.healthInforView = healthInforView;
    }

    //get news
    @Override
    public void getListArticles() {
        NewsApiServices newsApiServices= ApiUtils.getNewsApiService();
        Disposable disposable= newsApiServices.getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError, this::handleSuccess);
        compositeDisposable.add(disposable);
    }

    //get weather data
    @Override
    public void getWeatherData() {
        WeatherApiServices weatherApiServices= ApiUtils.getWeatherApiService();
        Disposable disposable= weatherApiServices.getHCMObs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError, this::handleSuccess);
        compositeDisposable.add(disposable);
    }

    private void handleResponse(CityInfor cityInfor) {
        cityInformation=cityInfor;
        healthInforView.getCityInforSuccess(cityInformation);
    }

    private void handleSuccess() {
    }

    private void handleError(Throwable throwable) {
        healthInforView.getListNewsFailed("Error"+throwable.getLocalizedMessage());
        healthInforView.getCityInforFailed("Error"+throwable.getLocalizedMessage());
    }

    private void handleResponse(News news) {
        articles= news.getArticles();
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

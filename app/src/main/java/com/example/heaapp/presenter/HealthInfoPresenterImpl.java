package com.example.heaapp.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;

import androidx.core.app.ActivityCompat;

import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.NewsApiServices;
import com.example.heaapp.api.WeatherApiServices;
import com.example.heaapp.model.airweather.CityInfor;
import com.example.heaapp.model.news.Article;
import com.example.heaapp.model.news.News;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.view.fragment.HealthInforView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HealthInfoPresenterImpl implements HealthInfoPresenter {

    private HealthInforView healthInforView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    public HealthInfoPresenterImpl(HealthInforView healthInforView, Context context) {
        this.healthInforView = healthInforView;
        this.context = context;
    }

    //get latest news and weather data
    @Override
    public void getLatestData() {
        NewsApiServices newsApiServices = ApiUtils.getNewsApiService();
        Disposable disposableNews = newsApiServices.getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError, this::handleSuccess);

        WeatherApiServices weatherApiServices = ApiUtils.getWeatherApiService();
        Disposable disposableWeather = weatherApiServices.getNearestCity(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()), Common.APIKEY)
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

    @Override
    public void permissionRequest() {
        Dexter.withActivity((Activity) context)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            setLocation();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        healthInforView.permissionDenied();
                    }
                }).check();
    }

    @Override
    public void setLocation() {
        buildLocationRequest();
        buildLocationCallBack();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private void buildLocationCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Common.current_location = locationResult.getLastLocation();
                getLatestData();
            }
        };
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(500);
        locationRequest.setSmallestDisplacement(10.0f);
    }


    private void handleResponse(CityInfor cityInfor) {
        healthInforView.getCityInfoSuccess(cityInfor);
    }

    private void handleSuccess() {
    }

    private void handleError(Throwable throwable) {
        healthInforView.getLatestDataFailed("Error" + throwable.getLocalizedMessage());
    }

    private void handleResponse(News news) {
        List<Article> articles = news.getArticles();
        healthInforView.getListNewsSuccess(articles);
    }


    @Override
    public void attachView(HealthInforView view) {
        healthInforView = view;
    }

    @Override
    public void detachView() {
        healthInforView = null;
    }
}

package com.example.heaapp.view.fragment;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.airweather.CityInfor;
import com.example.heaapp.model.news.Article;

import java.util.List;

public interface HealthInforView extends BaseView {
    void getListNewsSuccess(List<Article> articles);
    void getLatestDataFailed(String message);
    void getCityInfoSuccess(CityInfor cityInfor);
    void permissionDenied();
    void locationDisable();
}

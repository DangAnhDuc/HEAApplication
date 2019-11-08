package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.activity.SpashScreenView;

public interface SplashScreenPresenter extends BasePresenter<SpashScreenView> {
    void firstTimeInit();
    void dailyInit();
    void getFoodList();
}

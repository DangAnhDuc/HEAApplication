package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.fragment.HealthInforView;

public interface HealthInfoPresenter extends BasePresenter<HealthInforView> {
    void getLatestData();
    void disposeApi();
}

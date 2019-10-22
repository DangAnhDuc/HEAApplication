package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.fragment.HealthInforView;

public interface HealthInforPresenter extends BasePresenter<HealthInforView> {
    void getListArticles();
}

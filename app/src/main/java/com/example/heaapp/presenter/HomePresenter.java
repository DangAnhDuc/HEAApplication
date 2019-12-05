package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.activity.HomeView;

public interface HomePresenter extends BasePresenter<HomeView> {
    void getCurrentUser();
    void signOut();
    void onStart();
    void onStop();

}

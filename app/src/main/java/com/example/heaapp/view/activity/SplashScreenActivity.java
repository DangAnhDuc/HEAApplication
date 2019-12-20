package com.example.heaapp.view.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.heaapp.presenter.SplashScreenPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.ultis;

public class SplashScreenActivity extends AppCompatActivity implements SpashScreenView {
    SplashScreenPresenterImpl splashScreenPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmService realmService = RealmService.getInstance();
        splashScreenPresenter = new SplashScreenPresenterImpl(realmService, getContext());
        splashScreenPresenter.firstTimeInit();
        splashScreenPresenter.getFoodList();
        splashScreenPresenter.setlang();

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    ultis.setIntent(SplashScreenActivity.this, OnboardingActivity.class);
                    finish();
                }
            }
        };
        timer.start();
    }

    @Override
    public Context getContext() {
        return this;
    }

}

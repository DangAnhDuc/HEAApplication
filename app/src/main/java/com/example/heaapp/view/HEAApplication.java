package com.example.heaapp.view;

import android.app.Application;

import io.realm.Realm;

public class HEAApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}

package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;

import org.mozilla.geckoview.GeckoSession;

public interface WebviewNewsView extends BaseView {
    void setupSession(GeckoSession geckoSession);
    void onProgressLoading();
    void onProgressLoaded();
    void displayProgress(int progress);
}

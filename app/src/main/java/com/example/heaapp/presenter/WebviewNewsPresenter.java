package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.activity.WebviewNewsView;

public interface WebviewNewsPresenter extends BasePresenter<WebviewNewsView> {
    void initWebview(String url);
}

package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;

public interface HomeView extends BaseView {
    void setEnabled(boolean isEnabled);

    void setUser();
    void setIntentToLogin();
}

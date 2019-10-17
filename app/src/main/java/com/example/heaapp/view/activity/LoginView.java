package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;

public interface LoginView extends BaseView {
    void showValidationError(String message);
    void loginSuccess();
    void loginError();
    void isLogin(boolean isLogin);
    void setProgressVisibility(boolean visibility);

}

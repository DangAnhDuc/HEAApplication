package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;

public interface SignUpView extends BaseView {
    void showValidationError(String message);
    void signUpSuccess();
    void signUpError();
    void setProgressVisibility(boolean visibility);
    void showPasswordError();
    void showEmailError();
    void showNameError();
}

package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;

public interface SignUpView extends BaseView {
    void showValidationError();
    void signUpSuccess();
    void signUpError();
    void setProgressVisibility(boolean visibility);
}

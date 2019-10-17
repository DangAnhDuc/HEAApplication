package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.activity.SignUpView;

public interface SignUpPresenter extends BasePresenter<SignUpView> {
    void signUp(String name, String email,String password);
}

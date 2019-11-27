package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;

public interface UserInfoView extends BaseView {
    void onSaveInfoSuccess();
    void onSaveInfoFail(String error);
    void displayInfo(String name,String imgURL, int age, String sex, long weight, long height, long waist, long hip, long chest);
    void displayErrorMessage(String message);
}

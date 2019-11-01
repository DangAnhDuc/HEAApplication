package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.model.user_information.CurrentUserInfo;
import com.example.heaapp.view.activity.UserInforView;

import io.realm.RealmResults;

public interface UserInfoPresenter extends BasePresenter<UserInforView> {
    void loadInfo();
    void saveInfo(String age, String sex,String weight,String height, String waist, String hip, String chest);
    void saveUserInfoStatusPref();
}

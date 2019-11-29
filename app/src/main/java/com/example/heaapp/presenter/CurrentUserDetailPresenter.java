package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.activity.CurrentUserDetailView;

public interface CurrentUserDetailPresenter extends BasePresenter<CurrentUserDetailView> {
    void loadInfo();
    void saveInfo(String age, String sex,String weight,String height, String waist, String hip, String chest);
    void saveUserInfoStatusPref();
    void uploadImageFromGallery();
}

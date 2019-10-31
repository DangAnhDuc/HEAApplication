package com.example.heaapp.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.CurrentUserInfo;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.activity.HomeActivity;
import com.example.heaapp.view.activity.UserInforView;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserInfoPresenterImpl implements UserInfoPresenter, OnTransactionCallback {

    private UserInforView userInforView;
    private Context context;
    private final RealmService realmService;
    private Realm realm=Realm.getDefaultInstance();
    public UserInfoPresenterImpl(UserInforView userInforView, Context context,RealmService realmService) {
        this.userInforView = userInforView;
        this.context = context;
        this.realmService=realmService;
    }

    @Override
    public void loadInfo() {
        RealmResults<CurrentUserInfo> realmResults= realm.where(CurrentUserInfo.class)
                .equalTo("id",0)
                .findAll();
        userInforView.displayInfo(realmResults.get(0).getAge(),realmResults.get(0).getSex(),
                realmResults.get(0).getWeight(),realmResults.get(0).getHeight(),realmResults.get(0).getWaist(),
                realmResults.get(0).getHip(),realmResults.get(0).getChest());
    }

    @Override
    public void saveInfo(String age, String sex, String weight, String height, String waist, String hip, String chest) {
        if(TextUtils.isEmpty(age)||TextUtils.isEmpty(sex)||TextUtils.isEmpty(weight)||TextUtils.isEmpty(height)||TextUtils.isEmpty(waist)
                ||TextUtils.isEmpty(hip)||TextUtils.isEmpty(chest)){
            userInforView.displayErrorMessage("All field must be enter");
        }
        else {
            realmService.modifyUserInforAsync(Integer.parseInt(age),sex,Long.parseLong(weight),Long.parseLong(height)
                    ,Long.parseLong(waist),Long.parseLong(hip),Long.parseLong(chest),this);
        }
    }


    @Override
    public void saveUserInfoStatusPref() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isEntered", true);
        editor.apply();
    }

    @Override
    public void attachView(UserInforView view) {
        userInforView=view;
    }

    @Override
    public void detachView() {
        userInforView=null;
    }

    @Override
    public void onTransactionSuccess() {
        saveUserInfoStatusPref();
        userInforView.onSaveInfoSuccess();
    }

    @Override
    public void onTransactionError(Exception e) {
        userInforView.onSaveInfoFail(e.getMessage());
    }
}

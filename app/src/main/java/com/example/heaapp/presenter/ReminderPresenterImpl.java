package com.example.heaapp.presenter;

import android.util.Log;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.reminder.TimeReminder;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.view.activity.ReminderView;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

public class ReminderPresenterImpl implements ReminderPresenter{
    private ReminderView reminderView;
    private RealmService service;

    public ReminderPresenterImpl(ReminderView reminderView,RealmService service) {
        this.reminderView = reminderView;
        this.service = service;
    }


    @Override
    public void attachView(ReminderView view) {

    }

    @Override
    public void detachView() {

    }


    @Override
    public void saveDataReminder(int hour, int min, RealmList<String> listDay) {
        service.addReminder(hour,min,listDay, new OnTransactionCallback() {
            @Override
            public void onTransactionSuccess() {

            }

            @Override
            public void onTransactionError(Exception e) {

            }
        });
    }

    @Override
    public void loadDataReminder() {
        RealmResults<TimeReminder> realmResults = service.getReminder();
        for(int i = 0 ; i< realmResults.size(); i ++){
//            Log.d("debug", String.valueOf(realmResults.get(i).getDayList()));
            reminderView.LoadListDay(realmResults);
        }
    }
}

package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.activity.ReminderView;

import io.realm.RealmList;

public interface ReminderPresenter extends BasePresenter<ReminderView> {
    void saveDataReminder(int hour, int min, RealmList<String> listDay);
    void loadDataReminder();
}

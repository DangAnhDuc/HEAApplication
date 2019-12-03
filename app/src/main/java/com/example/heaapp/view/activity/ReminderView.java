package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.reminder.TimeReminder;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

public interface ReminderView extends BaseView {
    void LoadListDay(RealmResults<TimeReminder> realmResults);
}

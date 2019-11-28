package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.reminder.TimeReminder;

import java.util.List;

import io.realm.RealmList;

public interface ReminderView extends BaseView {
    void LoadListDay(int hour, int min, RealmList<String> list);
}

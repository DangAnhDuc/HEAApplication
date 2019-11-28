package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.activity.ReminderView;

public interface ReminderPresenter extends BasePresenter<ReminderView> {
    void saveDataReminder(int hour,int min);
}

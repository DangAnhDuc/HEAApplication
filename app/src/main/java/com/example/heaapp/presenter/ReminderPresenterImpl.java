package com.example.heaapp.presenter;

import com.example.heaapp.model.reminder.TimeReminder;
import com.example.heaapp.view.activity.ReminderView;

import java.util.ArrayList;
import java.util.List;

public class ReminderPresenterImpl implements ReminderPresenter{
    private ReminderView reminderView;

    public ReminderPresenterImpl(ReminderView reminderView) {
        this.reminderView = reminderView;
    }

    @Override
    public void addListReminder(int hour,int min) {
        reminderView.addListSuccess();
    }

    @Override
    public void attachView(ReminderView view) {

    }

    @Override
    public void detachView() {

    }


}

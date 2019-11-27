package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.reminder.TimeReminder;

import java.util.List;

public interface ReminderView extends BaseView {
    void addListSuccess(List<TimeReminder> list);
}

package com.example.heaapp.model.reminder;


import io.realm.RealmList;
import io.realm.RealmObject;

public class TimeReminder extends RealmObject {
    int hour;
    int minute;
    RealmList<String> dayList ;
    boolean isChecked;

    public TimeReminder(){}


    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public RealmList<String> getDayList() {
        return dayList;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setDayList(RealmList<String> dayList) {
        this.dayList = dayList;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

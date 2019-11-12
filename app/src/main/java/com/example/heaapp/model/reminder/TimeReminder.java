package com.example.heaapp.model.reminder;


import io.realm.RealmObject;

public class TimeReminder extends RealmObject {
    private int hour;
    private int minute;
//    private ArrayList<String> dayList ;
    private boolean isChecked;

    public TimeReminder(){}


    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

//    public List<String> getDayList() {
//        return dayList;
//    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

//    public void setDayList(ArrayList<String> dayList) {
//        this.dayList = dayList;
//    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

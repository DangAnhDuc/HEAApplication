package com.example.heaapp.view.activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.ReminderAdapter;
import com.example.heaapp.model.reminder.TimeReminder;
import com.example.heaapp.presenter.ReminderPresenter;
import com.example.heaapp.presenter.ReminderPresenterImpl;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class ReminderActivity extends AppCompatActivity implements ReminderView {
    @BindView(R.id.btn_add_reminder)
    FloatingActionButton btnAdd;
    @BindView(R.id.list_reminder)
    RecyclerView recyclerViewReminder;

    TimePickerDialog timePickerDialog;
    TimeReminder timeReminder = new TimeReminder();
    private int hour,min;
    private String[] dayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);

        initView();
//        ReminderPresenter presenter = new ReminderPresenterImpl(this);
//        presenter.addListReminder(hour,min);




    }

    private void initView() {

        btnAdd.setOnClickListener(v ->
                dialogAddTimeReminder()
        );


    }

    private void dialogAddTimeReminder() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(this, R.style.TimePicker, (view, hourOfDay, minute) -> {
            timeReminder.setHour(hourOfDay);
            timeReminder.setMinute(minute);
            Toast.makeText(this, hourOfDay+":"+minute, Toast.LENGTH_SHORT).show();
            dialogAddDayReminder();
        }, hours, minutes, false);
        timePickerDialog.show();
    }

    private void dialogAddDayReminder() {
        boolean[] num = {true,true,true,true,true,true,true};
        String[] listAllDay = getResources().getStringArray(R.array.day);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewReminder.setLayoutManager(layoutManager);
        androidx.appcompat.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(this, R.style.TimePicker);
        mBuilder.setTitle(getString(R.string.choose_day));
        mBuilder.setMultiChoiceItems(listAllDay,num , (dialog, which, isChecked) -> {
            if(isChecked ){
//                listAllDay =
            }
        }).setPositiveButton(android.R.string.ok, (dialog, which) -> {

        }).setNegativeButton(android.R.string.no, null);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void addListSuccess() {
        ReminderAdapter adapter = new ReminderAdapter(getContext(), hour,min);
        recyclerViewReminder.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Context getContext() {
        return this;
    }
}

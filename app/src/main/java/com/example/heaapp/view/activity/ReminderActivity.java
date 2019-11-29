package com.example.heaapp.view.activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.ReminderAdapter;
import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.reminder.TimeReminder;
import com.example.heaapp.presenter.ReminderPresenter;
import com.example.heaapp.presenter.ReminderPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ReminderActivity extends AppCompatActivity implements ReminderView {
    @BindView(R.id.btn_add_reminder)
    FloatingActionButton btnAdd;
    @BindView(R.id.list_reminder)
    RecyclerView recyclerViewReminder;
    @BindView(R.id.reminderToolbar)
    Toolbar reminderToolbar;
    TimePickerDialog timePickerDialog;
    private int hour,min;
    private RealmService service;
    ReminderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);

        initView();

//        presenter.addListReminder(hour,min);
    }

    private void initView() {
        //toolbar
        setSupportActionBar(reminderToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Reminder");
        reminderToolbar.setNavigationOnClickListener(v -> finish());


        //realmService


        btnAdd.setOnClickListener(v ->
                dialogAddTimeReminder()
        );
    }

    private void dialogAddTimeReminder() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(this, R.style.TimePicker, (view, hourOfDay, minute) -> {
            hour = hourOfDay;
            min = minute;
            dialogAddDayReminder();
        }, hours, minutes, false);
        timePickerDialog.show();
    }

    private void dialogAddDayReminder() {
        RealmList<String> listDay= new RealmList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewReminder.setLayoutManager(layoutManager);
        recyclerViewReminder.setAdapter(adapter);
        boolean[] num = {false,false,false,false,false,false,false};
//        boolean[] num = {true,true,true,true,true,true,true};
        String[] listAllDay = getResources().getStringArray(R.array.day);
        androidx.appcompat.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(this, R.style.TimePicker);
        mBuilder.setTitle(getString(R.string.choose_day));
        mBuilder.setMultiChoiceItems(listAllDay,num , (dialog, which, isChecked) -> {
            if(isChecked ){
                String a = listAllDay[which];
                listDay.add(a);
            }
        }).setPositiveButton(android.R.string.ok, (dialog, which) -> {
            callPresenterReminder(hour,min,listDay);
            adapter.notifyDataSetChanged();
        }).setNegativeButton(android.R.string.no, null);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private void callPresenterReminder(int hour,int min,RealmList<String> listDay){
        service = RealmService.getInstance();
        service.createTableReminder(new OnTransactionCallback() {
            @Override
            public void onTransactionSuccess() {

            }

            @Override
            public void onTransactionError(Exception e) {

            }
        });
        ReminderPresenter presenter = new ReminderPresenterImpl(this,service);
        presenter.saveDataReminder(hour,min,listDay);
        presenter.loadDataReminder();
    }



    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void LoadListDay(int hour, int min, RealmList<String> list) {
        List<Integer> listHour = new RealmList<>();
        listHour.add(hour);
        List<Integer> listMin = new RealmList<>();
        listMin.add(min);
        Log.d("info","hour: "+hour);
        Log.d("info","min: "+min);
        Log.d("info","day: "+list);
        adapter = new ReminderAdapter(getContext(),listHour,listMin,list);
    }
}

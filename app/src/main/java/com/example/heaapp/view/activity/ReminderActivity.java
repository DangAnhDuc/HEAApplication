package com.example.heaapp.view.activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.ReminderAdapter;
import com.example.heaapp.model.reminder.TimeReminder;
import com.example.heaapp.presenter.ReminderPresenter;
import com.example.heaapp.presenter.ReminderPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
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
    ReminderPresenter presenter;
    RealmResults<TimeReminder> realmResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        //toolbar
        setSupportActionBar(reminderToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Reminder");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewReminder.setHasFixedSize(true);
        recyclerViewReminder.setLayoutManager(layoutManager);

        service = RealmService.getInstance();
        presenter = new ReminderPresenterImpl(this,service);
        presenter.loadDataReminder();

        reminderToolbar.setNavigationOnClickListener(v -> finish());
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

        boolean[] num = {false,false,false,false,false,false,false};
        String[] listAllDay = getResources().getStringArray(R.array.day);
        androidx.appcompat.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(this, R.style.TimePicker);
        mBuilder.setTitle(getString(R.string.choose_day));
        mBuilder.setMultiChoiceItems(listAllDay,num , (dialog, which, isChecked) -> {
            if(isChecked ){
                String Day = listAllDay[which];
                listDay.add(Day);
            }
        }).setPositiveButton(android.R.string.ok, (dialog, which) -> {
            presenter.saveDataReminder(hour,min,listDay);
            refreshPage();
        }).setNegativeButton(android.R.string.no, null);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void LoadListDay(RealmResults<TimeReminder> realmResults) {
        recyclerViewReminder.setAdapter(adapter);
        this.realmResults = realmResults;
        adapter = new ReminderAdapter(getContext(),this.realmResults,service);
        adapter.setOnItemListener(v -> {
            service.removeReminder(v);
            refreshPage();
        });
        adapter.notifyDataSetChanged();
    }

    public void refreshPage(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public Context getContext() {
        return this;
    }
}

package com.example.heaapp.view.activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.ReminderAdapter;
import com.example.heaapp.model.reminder.TimeReminder;
import com.example.heaapp.presenter.ReminderPresenter;
import com.example.heaapp.presenter.ReminderPresenterImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderActivity extends AppCompatActivity implements ReminderView {
    @BindView(R.id.btn_add_reminder)
    FloatingActionButton btnAdd;
    @BindView(R.id.list_reminder)
    RecyclerView recyclerViewReminder;

    TimePickerDialog timePickerDialog;
    TimeReminder timeReminder = new TimeReminder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);
        initView();

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

            dialogAddDayReminder();
        }, hours, minutes, false);
        timePickerDialog.show();
    }

    private void dialogAddDayReminder() {
        boolean[] num = {true,true,true,true,true,true,true};
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewReminder.setLayoutManager(layoutManager);
        ReminderPresenter presenter = new ReminderPresenterImpl(this);
        androidx.appcompat.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(this, R.style.TimePicker);
        mBuilder.setTitle(getString(R.string.choose_day));
        mBuilder.setMultiChoiceItems(getResources().getStringArray(R.array.day),num , (dialog, which, isChecked) -> {
            presenter.addListReminder();
        })
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {

                }).setNegativeButton(android.R.string.no, null);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void addListSuccess(List<TimeReminder> list) {
        ReminderAdapter adapter = new ReminderAdapter(getContext(), list);
        recyclerViewReminder.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Context getContext() {
        return this;
    }
}

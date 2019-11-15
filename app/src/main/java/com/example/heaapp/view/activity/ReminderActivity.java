package com.example.heaapp.view.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heaapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderActivity extends AppCompatActivity {
    @BindView(R.id.btn_add_reminder)
    FloatingActionButton btnAdd;
    TimePickerDialog timePickerDialog;
    private int hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);
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
        androidx.appcompat.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(this, R.style.TimePicker);
        mBuilder.setTitle(getString(R.string.choose_day));
        mBuilder.setMultiChoiceItems(getResources().getStringArray(R.array.day), null, (dialog, which, isChecked) -> {

        })
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {

                }).setNegativeButton(android.R.string.no, null);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }
}

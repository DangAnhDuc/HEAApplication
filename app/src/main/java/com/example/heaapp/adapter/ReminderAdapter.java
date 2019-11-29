package com.example.heaapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.model.reminder.TimeReminder;
import com.example.heaapp.view.activity.ReminderView;

import java.util.Arrays;
import java.util.List;

import io.realm.RealmList;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private Context context;
    private RealmList<String> list;
    private List<Integer> hour,min;
    List<TimeReminder> listTime;
    ReminderView view;
    TimeReminder timeReminder = new TimeReminder();

    public ReminderAdapter(Context context, List<Integer> hour, List<Integer> min, RealmList listDay) {
        this.context = context;
        this.hour = hour;
        this.min = min;
        this.list = listDay;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.reminder_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String testListDay = String.valueOf(listTime.get(position).getDayList());
        testListDay = testListDay.replace(String.valueOf('['), "");
        testListDay = testListDay.replace(String.valueOf(']'), "");
        holder.txtTime.setText(listTime.get(position).getHour()+":"+listTime.get(position).getMinute());
        holder.txtDate.setText(testListDay);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTime,txtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txt_time_reminder);
            txtDate = itemView.findViewById(R.id.txt_day_reminder);
        }
    }
}

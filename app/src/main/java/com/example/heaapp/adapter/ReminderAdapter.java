package com.example.heaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.model.reminder.TimeReminder;
import com.example.heaapp.view.activity.ReminderView;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private Context context;
    private List<TimeReminder> list;
    private int hour,min;
    ReminderView view;
    TimeReminder timeReminder = new TimeReminder();

    public ReminderAdapter(Context context, int hour,int min) {
        this.context = context;
        this.hour = hour;
        this.min =  min;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.reminder_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTime.setText(timeReminder.getHour());
//        holder.txtDate.setText(String.valueOf(list.get(position).getDayList()));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTime,txtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txt_time_reminder);
//            txtDate = itemView.findViewById(R.id.txt_day_reminder);
        }
    }
}

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

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private Context context;
    private List<TimeReminder> list;

    public ReminderAdapter(Context context, List<TimeReminder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.reminder_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTime.setText((list.get(position).getHour()+":"+list.get(position).getMinute()));
//        holder.txtDate.setText(String.valueOf(list.get(position).getDayList()));
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

package com.example.heaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.callback.ReminderListener;
import com.example.heaapp.model.reminder.TimeReminder;
import com.example.heaapp.service.RealmService;

import io.realm.RealmList;
import io.realm.RealmResults;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private Context context;
    RealmResults<TimeReminder> realmResults;
    RealmService service;
    ReminderListener listener;
    String DayFortmatted;
    private static final int SIZE_LIST_FULL_WEEK = 7;
    private static final int SIZE_LIST_NORMAL_DAY = 5;

    public ReminderAdapter(Context context , RealmResults<TimeReminder> realmResults,RealmService service ) {
        this.context = context;
        this.realmResults = realmResults;
        this.service = service;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.reminder_item,parent,false);
        return new ViewHolder(view);
    }

    public void setOnItemListener(ReminderListener onItemListener) {
        this.listener = onItemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RealmList<String> ListDay = realmResults.get(position).getDayList();
        fortmatDay(String.valueOf(ListDay));
        holder.txtTime.setText(realmResults.get(position).getHour()+":"+realmResults.get(position).getMinute());
        if(ListDay.size() == SIZE_LIST_FULL_WEEK){
            holder.txtDate.setText(R.string.all_week);
        }else if (ListDay.size() == SIZE_LIST_NORMAL_DAY && !ListDay.contains("Saturday") && !ListDay.contains("Thứ 7")
                && !ListDay.contains("SunDay") && !ListDay.contains("Chủ Nhật")){
            holder.txtDate.setText(R.string.normal_day);
        }else {
            holder.txtDate.setText(DayFortmatted);
        }
        holder.imgDelete.setOnClickListener(v -> listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTime,txtDate;
        private ImageView imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txt_time_reminder);
            txtDate = itemView.findViewById(R.id.txt_day_reminder);
            imgDelete = itemView.findViewById(R.id.img_delete_reminder);
        }
    }

    private String fortmatDay(String day){
        DayFortmatted = day.replace(String.valueOf('['), "");
        DayFortmatted = DayFortmatted.replace(String.valueOf(']'), "");
        DayFortmatted = DayFortmatted.replace(("RealmList<java.lang.String>@"), "");
        return DayFortmatted;
    }

}

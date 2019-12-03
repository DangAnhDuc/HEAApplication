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

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private Context context;
    RealmResults<TimeReminder> realmResults;

    public ReminderAdapter(Context context , RealmResults<TimeReminder> realmResults ) {
        this.context = context;
        this.realmResults = realmResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.reminder_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String testListDay = String.valueOf(realmResults.get(position).getDayList());
        testListDay = testListDay.replace(String.valueOf('['), "");
        testListDay = testListDay.replace(String.valueOf(']'), "");
        holder.txtTime.setText(realmResults.get(position).getHour()+":"+realmResults.get(position).getMinute());
        holder.txtDate.setText(testListDay);
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
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

package com.example.heaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.model.workout.Activities;

import java.util.List;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {
    private Context context;
    private List<Activities> activitiesList;

    public ActivitiesAdapter(Context context, List<Activities> activitiesList) {
        this.context = context;
        this.activitiesList = activitiesList;
    }

    @NonNull
    @Override
    public ActivitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activities_item, parent, false);
        return new ActivitiesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_name.setText(activitiesList.get(position).getName());
        holder.tv_time.setText(String.format("in %s minutes", activitiesList.get(position).getTime()));
        holder.tv_burnedEnergy.setText(String.format("burned %skCal", activitiesList.get(position).getEnergy()));
    }

    @Override
    public int getItemCount() {
        return activitiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_time,tv_burnedEnergy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name= itemView.findViewById(R.id.tv_activities_name);
            tv_time= itemView.findViewById(R.id.tv_activities_time);
            tv_burnedEnergy= itemView.findViewById(R.id.tv_activities_burnedEnergy);

        }
    }
}

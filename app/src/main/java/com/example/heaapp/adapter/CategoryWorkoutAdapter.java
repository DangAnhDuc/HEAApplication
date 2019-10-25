package com.example.heaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.heaapp.R;
import com.example.heaapp.callback.OnItemClickListener;
import com.example.heaapp.callback.WorkoutListener;
import com.example.heaapp.model.workout.Results;
import com.example.heaapp.presenter.WorkoutPresenter;

import java.util.List;

public class CategoryWorkoutAdapter extends Adapter<CategoryWorkoutAdapter.ViewHolder> {
    private Context context;
    private WorkoutListener listener;
    private List<Results> listResults;

    public CategoryWorkoutAdapter(Context context, List<Results> listResults) {
        this.context = context;
        this.listResults = listResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.category_workout_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.viewBind(listResults.get(position),listener);
        holder.categoryName.setText(listResults.get(position).getName());
    }

    public void setOnItemListener(WorkoutListener workoutListener){
        this.listener = workoutListener;
    }

    @Override
    public int getItemCount() {
        return listResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView categoryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_workout_name);
        }

        public void viewBind(final Results results, WorkoutListener lis){
            itemView.setOnClickListener(v -> lis.OnItemClick(results));
        }
    }
}

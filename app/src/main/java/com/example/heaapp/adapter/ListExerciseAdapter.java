package com.example.heaapp.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.callback.ListExerciseListener;
import com.example.heaapp.model.workout.ItemExercise;
import com.example.heaapp.view.activity.ExerciseWorkoutActivity;

import java.util.List;


public class ListExerciseAdapter extends Adapter<ListExerciseAdapter.ViewHolder> {
    private Context context;
    private ListExerciseListener listener;
    private List<ItemExercise> list;
    private int categoryID;
    ExerciseWorkoutActivity exerciseWorkoutActivity = new ExerciseWorkoutActivity();

    public ListExerciseAdapter(Context context, List<ItemExercise> list,int categoryID) {
        this.context = context;
        this.list = list;
        this.categoryID = categoryID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_exercise_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListExerciseAdapter.ViewHolder holder, int position) {
        holder.viewBind(list.get(position),listener);
        Log.d("asdasda", "adapter"+String.valueOf(categoryID));
        if(list.get(position).getLanguage() == 2 && list.get(position).getName() != null && !list.get(position).getName().isEmpty() && categoryID == list.get(position).getCategory()) {
            holder.exerciseName.setText(list.get(position).getName());
        }
    }

    public void setOnItemListener(ListExerciseListener onItemListener){
        this.listener = onItemListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView exerciseName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_workout_name);
        }

        public void viewBind(ItemExercise item, ListExerciseListener listener){
            itemView.setOnClickListener(v -> listener.onItemListener(item));
        }
    }
}

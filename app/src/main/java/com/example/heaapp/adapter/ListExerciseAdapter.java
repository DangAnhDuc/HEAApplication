package com.example.heaapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.callback.ListExerciseListener;
import com.example.heaapp.model.workout.ItemExercise;
import com.example.heaapp.model.workout.ListExercise;

import java.util.List;

public class ListExerciseAdapter extends RecyclerView.Adapter<ListExerciseAdapter.ViewHolder> {
    private Context context;
    private ListExerciseListener listener;
    private List<ItemExercise> list;

    public ListExerciseAdapter(ListExerciseListener listener, List<ItemExercise> list) {
        this.listener = listener;
        this.list = list;
    }

    @NonNull
    @Override
    public ListExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_exercise_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListExerciseAdapter.ViewHolder holder, int position) {
        holder.viewBind(list.get(position),listener);
        holder.exerciseName.setText(list.get(position).getName());
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
            itemView.setOnClickListener(v->listener.onItemListener(item));
        }
    }
}

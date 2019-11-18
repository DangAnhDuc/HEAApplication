package com.example.heaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.heaapp.R;
import com.example.heaapp.callback.WorkoutListener;
import com.example.heaapp.model.workout.Category.Results;

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
        View view = LayoutInflater.from(context).inflate(R.layout.category_workout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.viewBind(listResults.get(position), listener);
        holder.categoryName.setText(listResults.get(position).getName());
        setImageView(position, holder);
    }

    public void setOnItemListener(WorkoutListener workoutListener) {
        this.listener = workoutListener;
    }

    @Override
    public int getItemCount() {
        return listResults.size();
    }

    private void setImageView(int pos, ViewHolder view) {
        switch (pos) {
            case 0:
                view.categoryImg.setImageResource(R.drawable.abs);
                break;
            case 1:
                view.categoryImg.setImageResource(R.drawable.arms);
                break;
            case 2:
                view.categoryImg.setImageResource(R.drawable.back);
                break;
            case 3:
                view.categoryImg.setImageResource(R.drawable.calves);
                break;
            case 4:
                view.categoryImg.setImageResource(R.drawable.chest);
                break;
            case 5:
                view.categoryImg.setImageResource(R.drawable.leg);
                break;
            case 6:
                view.categoryImg.setImageResource(R.drawable.shoulder);
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        private ImageView categoryImg;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_workout_name);
            categoryImg = itemView.findViewById(R.id.category_workout_img);
        }

        void viewBind(Results results, WorkoutListener lis) {
            itemView.setOnClickListener(v -> lis.OnItemClick(results));
        }
    }
}

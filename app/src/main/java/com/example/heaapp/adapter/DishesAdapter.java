package com.example.heaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.model.food.Dishes;

import java.util.List;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder> {
    private Context context;
    private List<Dishes> dishesList;

    public DishesAdapter(Context context, List<Dishes> dishesList) {
        this.context = context;
        this.dishesList = dishesList;
    }

    @NonNull
    @Override
    public DishesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dishes_item, parent, false);
        return new DishesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishesAdapter.ViewHolder holder, int position) {
        holder.tv_name.setText(dishesList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dishesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_food_name);
        }
    }
}

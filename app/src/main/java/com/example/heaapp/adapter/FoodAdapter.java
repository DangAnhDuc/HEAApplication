package com.example.heaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.heaapp.R;
import com.example.heaapp.callback.OnFoodClickListener;
import com.example.heaapp.callback.OnItemClickListener;
import com.example.heaapp.model.food.Data;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private Context context;
    private List<Data> foodData;
    private OnFoodClickListener listener;

    public FoodAdapter(Context context, List<Data> foodData) {
        this.context = context;
        this.foodData = foodData;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        holder.bind(foodData.get(position), listener);
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.news_animation));
        holder.tv_name.setText(foodData.get(position).getDisplayNameTranslations().getEn());
        try {
            String energy=String.format("%skCal", String.valueOf(foodData.get(position).getNutrients().getEnergyKcal().getPerHundred()));
            holder.tv_energy.setText(energy);
        }
        catch (Exception e){
            holder.tv_energy.setText("No info");
        }
        try {
            String carbs= String.format("%sg", String.valueOf(foodData.get(position).getNutrients().getCarbohydrates().getPerHundred()));
            holder.tv_carbs.setText(carbs);
        }
        catch (Exception e){
            holder.tv_carbs.setText("No info");
        }
        try {
            String protein=String.format("%sg", String.valueOf(foodData.get(position).getNutrients().getProtein().getPerHundred()));
            holder.tv_protein.setText(protein);
        }
        catch (Exception e){
            holder.tv_protein.setText("No info");
        }
        try {
            String fat=String.format("%sg", String.valueOf(foodData.get(position).getNutrients().getFat().getPerHundred()));
            holder.tv_fat.setText(fat);
        }
        catch (Exception e){
            holder.tv_fat.setText("No info");
        }
        try {
            Glide.with(context).load(foodData.get(position).getImages().get(1).getThumb()).into(holder.img_food);
        }
        catch (Exception e){
            Glide.with(context).load(foodData.get(position).getImages().get(0).getThumb()).into(holder.img_food);
        }
    }

    public void setOnItemListener(OnFoodClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return foodData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_energy,tv_carbs, tv_fat,tv_protein;
        ImageView img_food;
        LinearLayout container;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name= itemView.findViewById(R.id.food_name);
            tv_energy = itemView.findViewById(R.id.tv_energy_description);
            tv_carbs = itemView.findViewById((R.id.tv_carbs_description));
            tv_protein = itemView.findViewById((R.id.tv_protein_description));
            tv_fat = itemView.findViewById((R.id.tv_fat_description));

            img_food = itemView.findViewById(R.id.img_food);
            container = itemView.findViewById(R.id.layout_food_container);
        }

        public void bind(final Data data, final OnFoodClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(data));
        }
    }
}


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
import com.example.heaapp.callback.OnItemClickListener;
import com.example.heaapp.model.news.Article;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<Article> articles;
    private OnItemClickListener listener;

    public NewsAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(articles.get(position), listener);
        //holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.news_animation));
        holder.tv_title.setText(articles.get(position).getTitle());
        holder.tv_description.setText(articles.get(position).getDescription());
        Glide.with(context).load(articles.get(position).getUrlToImage()).into(holder.img_news);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_description;
        ImageView img_news;
        LinearLayout container;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_news_title);
            tv_description = itemView.findViewById((R.id.tv_description));
            img_news = itemView.findViewById(R.id.img_new);
            container = itemView.findViewById(R.id.layout_container);
        }

        public void bind(final Article article, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(article));
        }
    }
}

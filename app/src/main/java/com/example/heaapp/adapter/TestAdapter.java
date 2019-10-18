package com.example.heaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.model.Post;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter  extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    Context context;
    List<Post> mPostList;

    public TestAdapter(Context context, List<Post> mPostList) {
        this.context = context;
        this.mPostList = mPostList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(mPostList.get(position).getTitle());
        holder.tvcontent.setText(mPostList.get(position).getTitle());
        holder.tvauthor.setText(String.valueOf(mPostList.get(position).getUserId()));

    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle,tvcontent,tvauthor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle=(TextView) itemView.findViewById(R.id.tv_title);
            tvcontent=(TextView) itemView.findViewById(R.id.tv_content);
            tvauthor=(TextView) itemView.findViewById(R.id.tv_author);

        }
    }
}

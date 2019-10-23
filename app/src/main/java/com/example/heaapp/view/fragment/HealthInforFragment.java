package com.example.heaapp.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.heaapp.R;
import com.example.heaapp.adapter.NewsAdapter;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.callback.OnItemClickListener;
import com.example.heaapp.model.news.Article;
import com.example.heaapp.presenter.HealthInforPresenterImpl;
import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.activity.WebviewNewsActivity;

import java.util.List;


public class HealthInforFragment extends BaseFragment implements HealthInforView,SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView newsRecyclerview;
    private NewsAdapter newsAdapter;
    private HealthInforPresenterImpl healthInforPresenter;
    private List<Article> marticles;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public BaseFragment provideYourFragment() {
        return new HealthInforFragment();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_infor, parent, false);
        healthInforPresenter=new HealthInforPresenterImpl(this);
        newsRecyclerview= view.findViewById(R.id.new_recylcerview);
        newsRecyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        newsRecyclerview.setLayoutManager(layoutManager);

        swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary,R.color.colorPrimaryDark);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                healthInforPresenter.getListArticles();
            }
        });

        return view;
    }

    @Override
    public void getListNewsSuccess(List<Article> articles) {
        swipeRefreshLayout.setRefreshing(false);
        marticles= articles;
        newsAdapter=new NewsAdapter(getContext(),marticles);
        newsRecyclerview.setAdapter(newsAdapter);
        newsAdapter.setOnItemListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Article article) {
                Intent intent=new Intent(getContext(), WebviewNewsActivity.class);
                intent.putExtra("URL",article.getUrl());
                intent.putExtra("Domain",article.getSource().getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void getListNewsFailed(String message) {
        ultis.showMessage(getContext(),message);
    }


    @Override
    public void onRefresh() {
        healthInforPresenter.getListArticles();
    }
}

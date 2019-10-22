package com.example.heaapp.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.NewsAdapter;
import com.example.heaapp.api.AirApiServices;
import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.NewsApiServices;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.airweather.CityInfor;
import com.example.heaapp.model.news.Article;
import com.example.heaapp.model.news.News;
import com.example.heaapp.presenter.HealthInforPresenterImpl;
import com.example.heaapp.ultis.ultis;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class HealthInforFragment extends BaseFragment implements HealthInforView {
    RecyclerView newsRecyclerview;
    NewsAdapter newsAdapter;
    HealthInforPresenterImpl healthInforPresenter;
    List<Article> articles;
    private Dialog dialog;

    @Override
    public BaseFragment provideYourFragment() {
        return new HealthInforFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_infor, parent, false);
        healthInforPresenter=new HealthInforPresenterImpl(this);
        newsRecyclerview= view.findViewById(R.id.new_recylcerview);
        newsRecyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        newsRecyclerview.setLayoutManager(layoutManager);
        healthInforPresenter.getListArticles();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        healthInforPresenter.getListArticles();
    }

    @Override
    public void getListNewsSuccess(List<Article> articles) {
        this.articles= articles;
        newsAdapter=new NewsAdapter(getContext(),articles);
        newsAdapter.notifyData(articles);
        newsRecyclerview.setAdapter(newsAdapter);

    }

    @Override
    public void getListNewsFailed(String message) {
        ultis.showMessage(getContext(),message);
    }

}

package com.example.heaapp.view.fragment;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.news.Article;

import java.util.List;

public interface HealthInforView extends BaseView {
    void getListNewsSuccess(List<Article> articles);
    void getListNewsFailed(String message);

}

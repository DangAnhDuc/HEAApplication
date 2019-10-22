package com.example.heaapp.api;

import com.example.heaapp.model.airweather.CityInfor;
import com.example.heaapp.model.news.News;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NewsApiServices {
    @GET("top-headlines?country=us&category=health&apiKey=dcaf7b9b2b144366a7d8a6fd17824869")
    Observable<News> getNews();
}

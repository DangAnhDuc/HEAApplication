package com.example.heaapp.api;

import com.example.heaapp.model.airweather.CityInfor;
import com.example.heaapp.model.news.News;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NewsApiServices {
    @GET("top-headlines?country=us&category=health&apiKey=d6461abdc8c04f5aa93c2e5f448e285b")
    Observable<News> getNews();
}

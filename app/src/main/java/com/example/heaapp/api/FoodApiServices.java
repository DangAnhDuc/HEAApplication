package com.example.heaapp.api;

import com.example.heaapp.model.food.FoodInfor;
import com.example.heaapp.model.news.News;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoodApiServices {
    @GET("products/{id}")
    Observable<FoodInfor> getFoods(@Path("id") int id);
}

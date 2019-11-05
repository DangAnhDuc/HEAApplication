package com.example.heaapp.api;

import com.example.heaapp.model.food.FoodInfor;
import com.example.heaapp.model.news.News;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface FoodApiServices {
    @GET("products")
    Observable<FoodInfor> getFoods();
}

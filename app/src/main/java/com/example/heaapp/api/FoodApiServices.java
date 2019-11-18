package com.example.heaapp.api;

import com.example.heaapp.model.food.FoodInfor;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodApiServices {
    @GET("products/{id}")
    Observable<FoodInfor> getFoods(@Path("id") int id);
}

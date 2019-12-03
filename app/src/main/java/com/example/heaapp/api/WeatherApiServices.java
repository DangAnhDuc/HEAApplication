package com.example.heaapp.api;

import com.example.heaapp.model.airweather.CityInfor;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiServices {
    //get current weather in nearest city
    @GET("nearest_city")
    Observable<CityInfor> getNearestCity(@Query("lat") String lat, @Query("lon") String lon,@Query("key") String apikey);
}

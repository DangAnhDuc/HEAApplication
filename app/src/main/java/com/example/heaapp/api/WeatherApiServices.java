package com.example.heaapp.api;

import com.example.heaapp.model.airweather.CityInfor;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface WeatherApiServices {

    //key for api usage
    String APIKEY="2955facf-02ae-4d32-aacb-447f15c78f84";

    //get current weather in Ho Chi Minh City
    @GET("city?city=Ho Chi Minh City&state=Ho Chi Minh City&country=Vietnam&key=2955facf-02ae-4d32-aacb-447f15c78f84")
    Observable<CityInfor> getHCMObs();

    //get current weather in Ha Noi City
    @GET("city?city=Hanoi&state=Hanoi&country=Vietnam&key="+APIKEY)
    Observable<CityInfor> getHNObs();
}

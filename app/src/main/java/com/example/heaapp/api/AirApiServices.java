package com.example.heaapp.api;

import com.example.heaapp.model.CityInfor;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AirApiServices {
    String APIKEY="2955facf-02ae-4d32-aacb-447f15c78f84";
    @GET("city?city=Ho Chi Minh City&state=Ho Chi Minh City&country=Vietnam&key="+APIKEY)
    Observable<CityInfor> getHCMObs();

    @GET("city?city=Hanoi&state=Hanoi&country=Vietnam&key="+APIKEY)
    Observable<CityInfor> getHNObs();
}

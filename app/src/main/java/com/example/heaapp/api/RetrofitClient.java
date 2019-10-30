package com.example.heaapp.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static Retrofit retrofitNews = null;

    private static Retrofit retrofitWorkout = null;

    private static Retrofit retrofitWeather=null;

    private final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

    public static Retrofit getClientNews(String baseUrl) {
        if (retrofitNews == null) {
            retrofitNews = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofitNews;
    }


    public static Retrofit getRetrofitWorkOut(String baseUrl) {
        if (retrofitWorkout == null) {
            retrofitWorkout = new retrofit2.Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofitWorkout;
      }

    public static Retrofit getClientWeather(String baseUrl) {
        if (retrofitWeather == null) {
            retrofitWeather = new Retrofit.Builder()

                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofitWeather;
    }
}
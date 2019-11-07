package com.example.heaapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static Retrofit retrofitNews = null;

    private static Retrofit retrofitWorkout = null;

    private static Retrofit retrofitWeather=null;

    private static Retrofit retrofitExercise = null;

    private static Retrofit retrofitFoot=null;


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

    public static Retrofit getRetrofitExercise(String baseUrl) {
        if (retrofitExercise == null) {
            retrofitExercise = new retrofit2.Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofitExercise;
    }



    private final static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + "021fbcbdbd816b8748da0edb772c4d0a")
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    public static Retrofit getRetrofitFood(String baseUrl) {

        if (retrofitFoot == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofitFoot = new retrofit2.Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return retrofitFoot;
    }
}
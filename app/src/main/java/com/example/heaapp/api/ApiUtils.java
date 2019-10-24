package com.example.heaapp.api;

public class ApiUtils {

    private static final String WeatherApi = "https://api.airvisual.com/v2/";
    private static final String NewApi= "https://newsapi.org/v2/";

    public static WeatherApiServices getWeatherApiService() {
        return RetrofitClient.getClient1(WeatherApi).create(WeatherApiServices.class);
    }

    public static NewsApiServices getNewsApiService() {
        return RetrofitClient.getClient(NewApi).create(NewsApiServices.class);
    }
}

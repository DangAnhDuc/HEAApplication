package com.example.heaapp.api;

public class ApiUtils {

    private static final String WeatherApi = "https://api.airvisual.com/v2/";
    private static final String NewApi= "https://newsapi.org/v2/";
    private static final String WorkoutApi = "https://wger.de/api/v2/";

    public static WeatherApiServices getWeatherApiService() {
        return RetrofitClient.getClient1(WeatherApi).create(WeatherApiServices.class);
    }

    public static NewsApiServices getNewsApiService() {
        return RetrofitClient.getClient(NewApi).create(NewsApiServices.class);
    }

    public static WorkoutApiService getWorkoutApiService(){
        return RetrofitClient.getRetrofitWorkOut(WorkoutApi).create(WorkoutApiService.class);
    }

    public static WorkoutApiService getExerciseWorkoutApiService(){
        return RetrofitClient.getRetrofitExercise(WorkoutApi).create(WorkoutApiService.class);
    }
}

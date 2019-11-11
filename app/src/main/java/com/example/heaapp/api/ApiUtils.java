package com.example.heaapp.api;

public class ApiUtils {

    //base urls
    private static final String WeatherApi = "https://api.airvisual.com/v2/";
    private static final String NewApi= "https://newsapi.org/v2/";
    private static final String WorkoutApi = "https://wger.de/api/v2/";
    private static final String FoodApi="https://www.foodrepo.org/api/v3/";

    public static WeatherApiServices getWeatherApiService() {
        return RetrofitClient.getClientWeather(WeatherApi).create(WeatherApiServices.class);
    }

    public static NewsApiServices getNewsApiService() {
        return RetrofitClient.getClientNews(NewApi).create(NewsApiServices.class);
    }

    public static FoodApiServices getFoodApiServices(){
        return RetrofitClient.getRetrofitFood(FoodApi).create(FoodApiServices.class);
    }

    public static WorkoutApiService getWorkoutApiService(){
        return RetrofitClient.getRetrofitWorkOut(WorkoutApi).create(WorkoutApiService.class);
    }

    public static WorkoutApiService getExerciseWorkoutApiService(){

        return RetrofitClient.getRetrofitExercise(WorkoutApi).create(WorkoutApiService.class);
    }

    public static WorkoutApiService getMusclesApiService(){
        return RetrofitClient.getRetrofitExercise(WorkoutApi).create(WorkoutApiService.class);
    }
}

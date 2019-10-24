package com.example.heaapp.api;

public class ApiUtils {

    private static final String AirApi = "https://api.airvisual.com/v2/";
    private static final String NewApi= "https://newsapi.org/v2/";
    private static final String WorkoutApi = "https://wger.de/api/v2/";

    public static AirApiServices getAirApiService() {
        return RetrofitClient.getClient(AirApi).create(AirApiServices.class);
    }

    public static NewsApiServices getNewsApiService() {
        return RetrofitClient.getClient(NewApi).create(NewsApiServices.class);
    }

    public static WorkoutApiService getWorkoutApiService(){
        return RetrofitClient.getRetrofitWorkOut(WorkoutApi).create(WorkoutApiService.class);
    }
}

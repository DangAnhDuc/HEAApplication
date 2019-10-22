package com.example.heaapp.api;

public class ApiUtils {

    private static final String Airpi = "https://api.airvisual.com/v2/";

    public static AirApiServices getAirApiService() {
        return RetrofitClient.getClient(Airpi).create(AirApiServices.class);
    }
}

package com.example.heaapp.api;

public class ApiUtils {

    private static final String Airpi = "api.airvisual.com/v2/";
    private static final String base_url="https://jsonplaceholder.typicode.com/";
    public static AirApiServices getAirApiService() {
        return RetrofitClient.getClient(Airpi).create(AirApiServices.class);
    }

    public static TestApiServicers getTestApiServices(){
        return RetrofitClient.getClient(base_url).create(TestApiServicers.class);
    }
}

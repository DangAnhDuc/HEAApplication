package com.example.heaapp.api;



import com.example.heaapp.model.workout.Exercisecategory;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface WorkoutApiService {
    @GET("exercisecategory")
    Observable<Exercisecategory> getCategory();
}

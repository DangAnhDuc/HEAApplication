package com.example.heaapp.api;



import com.example.heaapp.model.workout.Exercisecategory;
import com.example.heaapp.model.workout.ListExercise;
import com.example.heaapp.model.workout.Muscle;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WorkoutApiService {

    // Get Category Exercise
    @GET("exercisecategory")
    Observable<Exercisecategory> getCategory();

    // Get Exercise Info
    @GET("exercise/")
    Observable<ListExercise> getExercisePage(@Query("page") int page);

    //Get Muscles
    @GET("muscle")
    Observable<Muscle> getMusclepage(@Query("page") int page);
}

package com.example.heaapp.api;


import com.example.heaapp.model.workout.category.Exercisecategory;
import com.example.heaapp.model.workout.equipment.Equipment;
import com.example.heaapp.model.workout.exerciseImage.ExerciseImage;
import com.example.heaapp.model.workout.exerciseInfo.ListExercise;
import com.example.heaapp.model.workout.muscle.Muscle;

import io.reactivex.Observable;
import retrofit2.http.GET;
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
    Observable<Muscle> getMuscle();

    //Get Equipment
    @GET("equipment")
    Observable<Equipment> getEquipment();

    //Get Image
    @GET("exerciseimage/")
    Observable<ExerciseImage> getImage(@Query("page") int page);
}

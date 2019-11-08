package com.example.heaapp.api;



import com.example.heaapp.model.workout.Category.Exercisecategory;
import com.example.heaapp.model.workout.Equipment.Equipment;
import com.example.heaapp.model.workout.ExerciseImage.ExerciseImage;
import com.example.heaapp.model.workout.ExerciseInfo.ListExercise;
import com.example.heaapp.model.workout.Muscle.Muscle;


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

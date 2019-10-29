package com.example.heaapp.api;



import com.example.heaapp.model.workout.Exercisecategory;
import com.example.heaapp.model.workout.ListExercise;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface WorkoutApiService {
    @GET("exercisecategory")
    Observable<Exercisecategory> getCategory();

    @GET("exercise/")
    Observable<ListExercise> getExercise();

    @GET("exercise/?page=2")
    Observable<ListExercise> getExercisePage2();
    @GET("exercise/?page=3")
    Observable<ListExercise> getExercisePage3();
    @GET("exercise/?page=4")
    Observable<ListExercise> getExercisePage4();
    @GET("exercise/?page=5")
    Observable<ListExercise> getExercisePage5();
    @GET("exercise/?page=6")
    Observable<ListExercise> getExercisePage6();
    @GET("exercise/?page=7")
    Observable<ListExercise> getExercisePage7();
    @GET("exercise/?page=8")
    Observable<ListExercise> getExercisePage8();
    @GET("exercise/?page=9")
    Observable<ListExercise> getExercisePage9();
    @GET("exercise/?page=10")
    Observable<ListExercise> getExercisePage10();
    @GET("exercise/?page=11")
    Observable<ListExercise> getExercisePage11();
    @GET("exercise/?page=12")
    Observable<ListExercise> getExercisePage12();
    @GET("exercise/?page=13")
    Observable<ListExercise> getExercisePage13();
    @GET("exercise/?page=14")
    Observable<ListExercise> getExercisePage14();
    @GET("exercise/?page=15")
    Observable<ListExercise> getExercisePage15();
    @GET("exercise/?page=16")
    Observable<ListExercise> getExercisePage16();
    @GET("exercise/?page=17")
    Observable<ListExercise> getExercisePage17();
    @GET("exercise/?page=18")
    Observable<ListExercise> getExercisePage18();
    @GET("exercise/?page=19")
    Observable<ListExercise> getExercisePage19();
    @GET("exercise/?page=20")
    Observable<ListExercise> getExercisePage20();
    @GET("exercise/?page=21")
    Observable<ListExercise> getExercisePage21();
    @GET("exercise/?page=22")
    Observable<ListExercise> getExercisePage22();
    @GET("exercise/?page=23")
    Observable<ListExercise> getExercisePage23();
    @GET("exercise/?page=24")
    Observable<ListExercise> getExercisePage24();
    @GET("exercise/?page=25")
    Observable<ListExercise> getExercisePage25();
    @GET("exercise/?page=26")
    Observable<ListExercise> getExercisePage26();
    @GET("exercise/?page=27")
    Observable<ListExercise> getExercisePage27();
    @GET("exercise/?page=28")
    Observable<ListExercise> getExercisePage28();
    @GET("exercise/?page=29")
    Observable<ListExercise> getExercisePage29();
    @GET("exercise/?page=30")
    Observable<ListExercise> getExercisePage30();




}

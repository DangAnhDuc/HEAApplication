package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.activity.ExerciseWorkoutView;

public interface ExerciseWorkoutPresenter extends BasePresenter<ExerciseWorkoutView > {
    void getListExercise();
}

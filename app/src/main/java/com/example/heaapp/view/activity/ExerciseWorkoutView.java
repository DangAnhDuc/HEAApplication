package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.workout.exerciseInfo.ItemExercise;

import java.util.List;

public interface ExerciseWorkoutView extends BaseView {
    void getListWorkoutSuccess(List<ItemExercise> list);
    void getError(String mes);
}

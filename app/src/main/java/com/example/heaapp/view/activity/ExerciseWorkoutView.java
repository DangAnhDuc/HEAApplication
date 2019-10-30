package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.workout.ItemExercise;

import java.util.List;

public interface ExerciseWorkoutView extends BaseView {
    void getListWorkoutSuccess(List<ItemExercise> list);
}

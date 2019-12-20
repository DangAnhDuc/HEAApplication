package com.example.heaapp.view.fragment;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.workout.category.Results;

import java.util.List;

public interface WorkoutView extends BaseView {
    void getListWorkoutSuccess(List<Results> articles);
}

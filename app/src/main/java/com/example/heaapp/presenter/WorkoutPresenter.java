package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.fragment.WorkoutView;

public interface WorkoutPresenter extends BasePresenter<WorkoutView> {
    void getListCategoryWorkout();
}

package com.example.heaapp.view.fragment;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.workout.ExerciseImage.Result;

import java.util.List;

public interface ExerciseImageView extends BaseView {
    void getImageSuccess(List<Result> list);
}

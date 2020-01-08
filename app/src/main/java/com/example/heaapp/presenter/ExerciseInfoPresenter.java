package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.fragment.ExerciseInfoView;

public interface ExerciseInfoPresenter extends BasePresenter<ExerciseInfoView> {
    void getListMuscle();
    void getListEquipment();
}

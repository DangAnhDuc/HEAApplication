package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.workout.equipment.Result;
import com.example.heaapp.model.workout.muscle.ListMuscle;

import java.util.List;

public interface ExerciseInfoView extends BaseView {
    void getMuscleSuccess(List<ListMuscle> nameMuscle);
    void getEquipSuccess(List<Result> nameEquip);
    void handleErrorListMusscle(String mes);
    void handleErrorEquip(String mes);
}

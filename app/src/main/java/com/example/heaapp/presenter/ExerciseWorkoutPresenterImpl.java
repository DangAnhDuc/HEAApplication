package com.example.heaapp.presenter;

import android.content.Context;

import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.WorkoutApiService;
import com.example.heaapp.model.workout.ItemExercise;
import com.example.heaapp.model.workout.ListExercise;
import com.example.heaapp.view.activity.ExerciseWorkoutView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExerciseWorkoutPresenterImpl implements ExerciseWorkoutPresenter{

    private List<ItemExercise> listItem;
    private ExerciseWorkoutView exerciseView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ExerciseWorkoutPresenterImpl(ExerciseWorkoutView exerciseView) {
        this.exerciseView = exerciseView;
    }

    @Override
    public void getListExercise() {
        WorkoutApiService workoutApiService= ApiUtils.getExerciseWorkoutApiService();
        Disposable disposable = workoutApiService.getExercise()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleReponse);
        compositeDisposable.add(disposable);
    }

    @Override
    public void attachView(ExerciseWorkoutView view) {
        exerciseView = view;
    }

    @Override
    public void detachView() {
        exerciseView= null;
    }

    private void handleReponse(ListExercise listExercise){
        listItem = listExercise.getResults();
        exerciseView.getListWorkoutSuccess(listItem);
    }
}

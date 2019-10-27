package com.example.heaapp.presenter;

import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.WorkoutApiService;
import com.example.heaapp.base.BaseView;
import com.example.heaapp.model.workout.Exercisecategory;
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
    private ExerciseWorkoutView view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
    public void attachView(BaseView view) {

    }

    @Override
    public void detachView() {

    }

    private void handleReponse(ListExercise listExercise){
        listItem = listExercise.getResults();
        view.getListWorkoutSuccess(listItem);
    }
}

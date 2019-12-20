package com.example.heaapp.presenter;


import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.WorkoutApiService;
import com.example.heaapp.model.workout.category.Exercisecategory;
import com.example.heaapp.model.workout.category.Results;
import com.example.heaapp.view.fragment.WorkoutView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WorkoutPresenterImpl implements WorkoutPresenter {
    private WorkoutView workoutView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public WorkoutPresenterImpl(WorkoutView view) {
        this.workoutView = view;
    }

    @Override
    public void getListCategoryWorkout() {
        WorkoutApiService workoutApiService = ApiUtils.getWorkoutApiService();
        Disposable disposable = workoutApiService.getCategory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleReponse);
        compositeDisposable.add(disposable);
    }

    @Override
    public void attachView(WorkoutView view) {
        workoutView = view;
    }

    @Override
    public void detachView() {
        workoutView = null;
    }

    private void handleReponse(Exercisecategory exercisecategory) {
        List<Results> listCategory = exercisecategory.getResults();
        workoutView.getListWorkoutSuccess(listCategory);
    }
}

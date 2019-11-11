package com.example.heaapp.presenter;

import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.WorkoutApiService;
import com.example.heaapp.model.workout.ExerciseImage.ExerciseImage;
import com.example.heaapp.model.workout.ExerciseImage.Result;
import com.example.heaapp.view.activity.ExerciseImageView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExerciseImagePresenterImpl implements ExerciseImagePresenter {
    private List<Result> results,resultsAfterCompare = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ExerciseImageView imageView;
    private int exeID;

    public ExerciseImagePresenterImpl(ExerciseImageView exerciseImageView, int exeID) {
        this.imageView = exerciseImageView;
        this.exeID = exeID;
    }

    @Override
    public void getListImage() {
        WorkoutApiService workoutApiService = ApiUtils.getExerciseWorkoutApiService();
        Disposable disposable;
        for(int i = 1; i<= 11 ; i++){
            disposable = workoutApiService.getImage(i)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleReponse);
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void attachView(ExerciseImageView view) {

    }

    @Override
    public void detachView() {

    }

    private void handleReponse(ExerciseImage exerciseImage){
        results = exerciseImage.getResults();
        for(int i = 0 ; i<results.size()-1;i++){
            if(results.get(i).getExercise()==26){
                resultsAfterCompare.add(results.get(i));
            }
        }
        imageView.getImageSuccess(resultsAfterCompare);
    }
}

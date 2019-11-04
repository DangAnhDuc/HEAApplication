package com.example.heaapp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.WorkoutApiService;
import com.example.heaapp.model.workout.ItemExercise;
import com.example.heaapp.model.workout.ListExercise;
import com.example.heaapp.view.activity.ExerciseWorkoutView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExerciseWorkoutPresenterImpl implements ExerciseWorkoutPresenter {
    private List<ItemExercise> listItem;
    private ExerciseWorkoutView exerciseView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int CateID;

    List<ItemExercise> list = new ArrayList<>();

    public ExerciseWorkoutPresenterImpl(int Cateid, ExerciseWorkoutView exerciseView) {
        this.exerciseView = exerciseView;
        this.CateID = Cateid;
    }

    @Override
    public void getListExercise() {
        WorkoutApiService workoutApiService = ApiUtils.getExerciseWorkoutApiService();
        Disposable disposable;
        for(int i = 1 ;i <= 30; i++){
            disposable = workoutApiService.getExercisePage(i)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleReponse);
            compositeDisposable.add(disposable);


        }
    }

    @Override
    public void attachView(ExerciseWorkoutView view) {
        exerciseView = view;
    }

    @Override
    public void detachView() {
        exerciseView = null;
        compositeDisposable.dispose();
    }

    private void handleReponse(ListExercise listExercise) {
        listItem = listExercise.getResults();
        try {
            for (int i = 0; i < listItem.size() - 1; i++) {
                if (listItem.get(i).getLanguage() == 2 && !TextUtils.isEmpty(listItem.get(i).getName()) && listItem.get(i).getCategory() == CateID) {
                    list.add(listItem.get(i));
                    Log.d("test", listItem.get(i).getName() + ".." + listItem.get(i).getId() + ".." + listItem.get(i).getCategory());
                }
            }
            exerciseView.getListWorkoutSuccess(list);
        } catch (Exception ex) {
            Log.d("bug", String.valueOf(ex));
        }
    }
}

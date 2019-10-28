package com.example.heaapp.presenter;

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

public class ExerciseWorkoutPresenterImpl implements ExerciseWorkoutPresenter {
    private String nextApi = "";
    private List<ItemExercise> listItem;
    private ExerciseWorkoutView exerciseView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ExerciseWorkoutPresenterImpl(ExerciseWorkoutView exerciseView) {
        this.exerciseView = exerciseView;
    }

    @Override
    public void getListExercise() {
        WorkoutApiService workoutApiService = ApiUtils.getExerciseWorkoutApiService();
        Disposable disposable = workoutApiService.getExercise()
                .mergeWith(workoutApiService.getExercisePage2())
                .mergeWith(workoutApiService.getExercisePage3())
                .mergeWith(workoutApiService.getExercisePage4())
                .mergeWith(workoutApiService.getExercisePage5())
                .mergeWith(workoutApiService.getExercisePage6())
                .mergeWith(workoutApiService.getExercisePage7())
                .mergeWith(workoutApiService.getExercisePage8())
                .mergeWith(workoutApiService.getExercisePage9())
                .mergeWith(workoutApiService.getExercisePage10())
                .mergeWith(workoutApiService.getExercisePage11())
                .mergeWith(workoutApiService.getExercisePage12())
                .mergeWith(workoutApiService.getExercisePage13())
                .mergeWith(workoutApiService.getExercisePage14())
                .mergeWith(workoutApiService.getExercisePage15())
                .mergeWith(workoutApiService.getExercisePage16())
                .mergeWith(workoutApiService.getExercisePage17())
                .mergeWith(workoutApiService.getExercisePage18())
                .mergeWith(workoutApiService.getExercisePage19())
                .mergeWith(workoutApiService.getExercisePage20())
                .mergeWith(workoutApiService.getExercisePage21())
                .mergeWith(workoutApiService.getExercisePage22())
                .mergeWith(workoutApiService.getExercisePage23())
                .mergeWith(workoutApiService.getExercisePage24())
                .mergeWith(workoutApiService.getExercisePage25())
                .mergeWith(workoutApiService.getExercisePage26())
                .mergeWith(workoutApiService.getExercisePage27())
                .mergeWith(workoutApiService.getExercisePage28())
                .mergeWith(workoutApiService.getExercisePage29())
                .mergeWith(workoutApiService.getExercisePage30())
                .mergeWith(workoutApiService.getExercisePage31())
                .mergeWith(workoutApiService.getExercisePage32())
                .mergeWith(workoutApiService.getExercisePage33())
                .mergeWith(workoutApiService.getExercisePage34())
                .mergeWith(workoutApiService.getExercisePage35())
                .mergeWith(workoutApiService.getExercisePage36())
                .mergeWith(workoutApiService.getExercisePage37())
                .mergeWith(workoutApiService.getExercisePage38())
                .mergeWith(workoutApiService.getExercisePage39())
                .mergeWith(workoutApiService.getExercisePage40())
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
        exerciseView = null;
    }

    private void handleReponse(ListExercise listExercise) {
        listItem = listExercise.getResults();
        nextApi = listExercise.getNext();
        exerciseView.getListWorkoutSuccess(listItem);
    }
}

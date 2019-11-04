package com.example.heaapp.presenter;

import android.util.Log;

import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.WorkoutApiService;
import com.example.heaapp.model.workout.Equipment.Equipment;
import com.example.heaapp.model.workout.Equipment.Result;
import com.example.heaapp.model.workout.Muscle.ListMuscle;
import com.example.heaapp.model.workout.Muscle.Muscle;
import com.example.heaapp.view.activity.ExerciseInfoView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExerciseInfoPresenterImpl implements ExerciseInfoPresenter {
    private ExerciseInfoView view;
    private ArrayList<Integer> cateID,equipID;
    private List<ListMuscle> nameMuscle = new ArrayList<>();
    private List<Result> nameEquip = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CompositeDisposable compositeDisposableEquip = new CompositeDisposable();

    public ExerciseInfoPresenterImpl(ArrayList<Integer> cateID,ArrayList<Integer> equipID, ExerciseInfoView view) {
        this.equipID = equipID;
        this.cateID = cateID;
        this.view = view;
    }


    @Override
    public void getListMuscle() {
        WorkoutApiService workoutApiService = ApiUtils.getExerciseWorkoutApiService();
        Disposable disposable;

        disposable = workoutApiService.getMuscle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleReponse);
        compositeDisposable.add(disposable);
    }

    @Override
    public void getListEquipment() {
        WorkoutApiService workoutApiService = ApiUtils.getExerciseWorkoutApiService();
        Disposable disposable;

        disposable = workoutApiService.getEquipment()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleReponseEquipment);
        compositeDisposableEquip.add(disposable);
    }

    private void handleReponse(Muscle muscle) {

        List<ListMuscle> listMuscles = muscle.getResults();
        for(int i = 0; i <= cateID.size() -1 ; i++){
            for(int y = 0; y<= listMuscles.size()-1 ; y++){
                if (cateID.get(i) == (listMuscles.get(y).getId())){
                    nameMuscle.add(listMuscles.get(y));
                }
            }
        }try{
            view.getMuscleSuccess(nameMuscle);
        }catch (Exception ex) {
            Log.d("Error" , String.valueOf(ex));
        }

    }


    private void handleReponseEquipment(Equipment equipment){
        List<Result> listEquipment = equipment.getResults();
        for(int i = 0; i <= equipID.size() -1 ; i++){
            for(int y = 0; y<= listEquipment.size()-1 ; y++){
                Log.d("log","equipID"+equipID.get(i));
                Log.d("log","listEquipment"+listEquipment.get(y).getId());
                if (equipID.get(i) == (listEquipment.get(y).getId())){
                    nameEquip.add(listEquipment.get(y));
                }
            }
        }try{
            view.getEquipSuccess(nameEquip);
        }catch (Exception ex) {
            Log.d("Error" , String.valueOf(ex));
        }
    }

    @Override
    public void attachView(ExerciseInfoView view) {

    }

    @Override
    public void detachView() {

    }
}

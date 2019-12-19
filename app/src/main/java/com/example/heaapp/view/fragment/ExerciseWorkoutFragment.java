package com.example.heaapp.view.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.ListExerciseAdapter;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.workout.ExerciseInfo.ItemExercise;
import com.example.heaapp.presenter.ExerciseWorkoutPresenter;
import com.example.heaapp.presenter.ExerciseWorkoutPresenterImpl;
import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.activity.ExerciseInfoActivity;
import com.example.heaapp.view.activity.ExerciseWorkoutView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseWorkoutFragment extends BaseFragment implements ExerciseWorkoutView {
    @BindView(R.id.exerciseToolbar)
    Toolbar exerciseToolBar;
    @BindView(R.id.exeText)
    TextView exeText;
    @BindView(R.id.list_exercise_fragment)
    RecyclerView recyclerViewList;
    @BindView(R.id.linearListExercise)
    LinearLayout linearLayoutExercise;
    private ExerciseWorkoutPresenter exerciseWorkoutPresenter;
    private List<ItemExercise> listExercise;
    AlertDialog dialog;
    ListExerciseAdapter listExerciseAdapter;

    private Unbinder unbinder;
    @Override
    public BaseFragment provideYourFragment() {
        return new ExerciseWorkoutFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_workout, parent, false);
        unbinder = ButterKnife.bind(getContext(), view);

//        String a = this.getArguments().getString("CategoryName");
        int categoryID ;
        Bundle bundle = getArguments();
        categoryID = bundle.getInt("CategoryID");

        recyclerViewList.setHasFixedSize(true);
        exerciseWorkoutPresenter = new ExerciseWorkoutPresenterImpl(categoryID, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(layoutManager);
        linearLayoutExercise.post(() -> exerciseWorkoutPresenter.getListExercise());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void getListWorkoutSuccess(List<ItemExercise> list) {
        dialog.dismiss();
        listExerciseAdapter = new ListExerciseAdapter(getContext(), list);
        recyclerViewList.setAdapter(listExerciseAdapter);
        listExerciseAdapter.notifyDataSetChanged();
        listExerciseAdapter.setOnItemListener(listExercise -> {
            Intent intent = new Intent(getContext(), ExerciseInfoActivity.class);
            intent.putExtra("name",listExercise.getName());
            intent.putExtra("description",listExercise.getDescription());
            intent.putExtra("id",listExercise.getId());
            intent.putIntegerArrayListExtra("muscles", (ArrayList<Integer>) listExercise.getMuscles());
            intent.putIntegerArrayListExtra("equipment", (ArrayList<Integer>) listExercise.getEquipment());
            startActivity(intent);
        });
    }

    @Override
    public void getError(String mes) {
            ultis.showErrorMessage(getContext(), mes);
    }
}

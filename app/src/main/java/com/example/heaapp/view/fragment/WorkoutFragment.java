package com.example.heaapp.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.CategoryWorkoutAdapter;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.workout.Category.Results;
import com.example.heaapp.presenter.WorkoutPresenter;
import com.example.heaapp.presenter.WorkoutPresenterImpl;
import com.example.heaapp.view.activity.ExerciseWorkoutActivity;

import java.util.List;
import java.util.Objects;


public class WorkoutFragment extends BaseFragment implements WorkoutView {
    private RecyclerView categoryLayout;
    private WorkoutPresenter workoutPresenter;
    private ProgressDialog dialog;


    @Override
    public BaseFragment provideYourFragment() {
        return new WorkoutFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, parent, false);
        dialog = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);

        categoryLayout = view.findViewById(R.id.category_workout_fragment);
        categoryLayout.setHasFixedSize(true);
        workoutPresenter = new WorkoutPresenterImpl(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        categoryLayout.setLayoutManager(layoutManager);
        return view;
    }


    @Override
    public void getListWorkoutSuccess(List<Results> results) {
        dialog.dismiss();
        CategoryWorkoutAdapter categoryWorkoutAdapter = new CategoryWorkoutAdapter(getContext(), results);
        categoryLayout.setAdapter(categoryWorkoutAdapter);

        categoryWorkoutAdapter.setOnItemListener(results1 -> {
            Intent intent = new Intent(getContext(), ExerciseWorkoutActivity.class);
            intent.putExtra("CategoryID", results1.getId());
            intent.putExtra("CategoryName", results1.getName());
            startActivity(intent);
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!getUserVisibleHint()) {
            return;
        }
        dialog.setIndeterminate(true);
        dialog.setMessage(getString(R.string.msg_loading));
        dialog.setCancelable(false);
        dialog.show();
        workoutPresenter.getListCategoryWorkout();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

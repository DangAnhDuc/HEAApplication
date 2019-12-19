package com.example.heaapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.heaapp.R;
import com.example.heaapp.adapter.CategoryWorkoutAdapter;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.workout.Category.Results;
import com.example.heaapp.presenter.WorkoutPresenter;
import com.example.heaapp.presenter.WorkoutPresenterImpl;
import com.example.heaapp.view.activity.ExerciseWorkoutActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class WorkoutFragment extends BaseFragment implements WorkoutView {
    @BindView(R.id.category_workout_recycler)
    ShimmerRecyclerView categoryWorkoutRecycler;
    private WorkoutPresenter workoutPresenter;
    private Unbinder unbinder;

    @Override
    public BaseFragment provideYourFragment() {
        return new WorkoutFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, parent, false);
        unbinder = ButterKnife.bind(this, view);
        workoutPresenter = new WorkoutPresenterImpl(this);
        categoryWorkoutRecycler.showShimmerAdapter();
        return view;
    }


    @Override
    public void getListWorkoutSuccess(List<Results> results) {
        CategoryWorkoutAdapter categoryWorkoutAdapter = new CategoryWorkoutAdapter(getContext(), results);
        categoryWorkoutRecycler.setAdapter(categoryWorkoutAdapter);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }
}

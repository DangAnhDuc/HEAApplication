package com.example.heaapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.CategoryWorkoutAdapter;
import com.example.heaapp.adapter.ListExerciseAdapter;
import com.example.heaapp.model.workout.ItemExercise;
import com.example.heaapp.presenter.ExerciseWorkoutPresenter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseWorkoutActivity extends AppCompatActivity implements ListExerciseVeiw{
    @BindView(R.id.exerciseToolbar)
    Toolbar exerciseToolBar;
    @BindView(R.id.exeText)
    TextView exeText;
    @BindView(R.id.list_exercise_fragment)
    RecyclerView recyclerViewList;
    @BindView(R.id.linearListExercise)
    LinearLayout linearLayoutExercise;
    private ListExerciseAdapter listExerciseAdapter;
    private ExerciseWorkoutPresenter exerciseWorkoutPresenter;
    private List<ItemExercise> listExercise;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_workout);
        ButterKnife.bind(this);
        initView();
    }

    public void initView(){
        //set toolbar
        setSupportActionBar(exerciseToolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //set Text for Exercise Title
        Bundle bundle = getIntent().getExtras();
        exeText.setText(bundle.getString("CategoryName"));

        exerciseToolBar.setNavigationOnClickListener(v -> finish());

        recyclerViewList.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewList.setLayoutManager(layoutManager);

        linearLayoutExercise.post(()->exerciseWorkoutPresenter.getListExercise());
    }



    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void getListExerciseViewSuccess(List<ItemExercise> item) {
        listExercise = item;
        ListExerciseAdapter listExerciseAdapter = new ListExerciseAdapter(getContext(),listExercise);
        recyclerViewList.setAdapter(listExerciseAdapter);

//        ListExerciseAdapter(results1 -> {
//            Intent intent = new Intent(getContext(), ExerciseWorkoutActivity.class);
//            intent.putExtra("CategoryName",results1.getName());
//            startActivity(intent);
//        });
    }
}

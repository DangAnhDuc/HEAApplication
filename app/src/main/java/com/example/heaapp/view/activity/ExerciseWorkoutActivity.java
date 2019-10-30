package com.example.heaapp.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.ListExerciseAdapter;
import com.example.heaapp.model.workout.ItemExercise;
import com.example.heaapp.presenter.ExerciseWorkoutPresenter;
import com.example.heaapp.presenter.ExerciseWorkoutPresenterImpl;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseWorkoutActivity extends AppCompatActivity implements ExerciseWorkoutView{
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
    private int categoryID ;
    ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_workout);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dialog.setIndeterminate(true);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
    }

    public void initView(){
        dialog = new ProgressDialog(getContext(),R.style.AppTheme_Dark_Dialog);

        //set toolbar
        setSupportActionBar(exerciseToolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //set Text for Exercise Title
        Bundle bundle = getIntent().getExtras();
        exeText.setText(bundle.getString("CategoryName"));
        categoryID = bundle.getInt("CategoryID");
        exerciseToolBar.setNavigationOnClickListener(v -> finish());

        recyclerViewList.setHasFixedSize(true);

        exerciseWorkoutPresenter = new ExerciseWorkoutPresenterImpl(categoryID,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(layoutManager);

        linearLayoutExercise.post(()->exerciseWorkoutPresenter.getListExercise());
    }

    @Override
    public void getListWorkoutSuccess(List<ItemExercise> list) {
        dialog.dismiss();
        listExercise = list;

        ListExerciseAdapter listExerciseAdapter = new ListExerciseAdapter(getContext(),listExercise);
        recyclerViewList.setAdapter(listExerciseAdapter);
        listExerciseAdapter.notifyDataSetChanged();
        listExerciseAdapter.setOnItemListener(listExercise-> Log.d("textString",listExercise.getName()));
    }

    @Override
    public Context getContext() {
        return this;
    }
}

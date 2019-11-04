package com.example.heaapp.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.ListExerciseAdapter;
import com.example.heaapp.model.workout.ExerciseInfo.ItemExercise;
import com.example.heaapp.presenter.ExerciseWorkoutPresenter;
import com.example.heaapp.presenter.ExerciseWorkoutPresenterImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseWorkoutActivity extends AppCompatActivity implements ExerciseWorkoutView {
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
    private int categoryID;
    ProgressDialog dialog;
    ListExerciseAdapter listExerciseAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_workout);
        ButterKnife.bind(this);
        initView();

        dialog.setIndeterminate(true);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
    }


    public void initView() {
        dialog = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);

        //set toolbar
        setSupportActionBar(exerciseToolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        exerciseToolBar.setNavigationOnClickListener(v -> finish());

        //set Text for Exercise Title
        Bundle bundle = getIntent().getExtras();
        exeText.setText(bundle.getString("CategoryName"));
        categoryID = bundle.getInt("CategoryID");
        recyclerViewList.setHasFixedSize(true);
        exerciseWorkoutPresenter = new ExerciseWorkoutPresenterImpl(categoryID, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(layoutManager);
        linearLayoutExercise.post(() -> exerciseWorkoutPresenter.getListExercise());
        dragAndDrop();

    }


    @Override
    public void getListWorkoutSuccess(List<ItemExercise> list) {
        dialog.dismiss();
        listExerciseAdapter = new ListExerciseAdapter(getContext(), list);
        recyclerViewList.setAdapter(listExerciseAdapter);
        listExerciseAdapter.notifyDataSetChanged();
        listExerciseAdapter.setOnItemListener(listExercise -> {
            Intent intent = new Intent(this,ExerciseInfoActivity.class);
            intent.putExtra("name",listExercise.getName());
            Log.d("Error",listExercise.getName());
            intent.putExtra("description",listExercise.getDescription());
            intent.putIntegerArrayListExtra("muscles", (ArrayList<Integer>) listExercise.getMuscles());
            intent.putIntegerArrayListExtra("equipment", (ArrayList<Integer>) listExercise.getEquipment());
            startActivity(intent);
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void dragAndDrop() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int pos_drag = viewHolder.getAdapterPosition();
                int pos_target = viewHolder.getAdapterPosition();

                Collections.swap(listExercise, pos_drag, pos_target);
                listExerciseAdapter.notifyItemMoved(pos_drag, pos_target);
                listExerciseAdapter.notifyDataSetChanged();
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewList);
    }
}

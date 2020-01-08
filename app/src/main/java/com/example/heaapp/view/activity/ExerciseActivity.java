package com.example.heaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.example.heaapp.R;
import com.example.heaapp.view.fragment.ExerciseWorkoutFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseActivity extends AppCompatActivity {
    @BindView(R.id.workoutToolbar)
    Toolbar toolbar;
    @BindView(R.id.workout_name)
    TextView txtNameCate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        ButterKnife.bind(this);
        toolbar();
        initView();
    }

    private void initView(){
        ExerciseWorkoutFragment fragment = new ExerciseWorkoutFragment();
        FragmentManager manager = getSupportFragmentManager();

        Bundle bundle = getIntent().getExtras();
        txtNameCate.setText(bundle.getString("CategoryName"));
        int b = bundle.getInt("CategoryID");

        Bundle bundle1 = new Bundle();
        bundle1.putInt("CategoryID",b);
        fragment.setArguments(bundle1);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.ExerciseFragment,fragment);
        transaction.commit();
    }

    private void toolbar(){
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}

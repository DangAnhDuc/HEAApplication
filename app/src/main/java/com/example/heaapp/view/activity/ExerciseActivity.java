package com.example.heaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.heaapp.R;
import com.example.heaapp.view.fragment.ExerciseWorkoutFragment;

public class ExerciseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        initView();
    }

    private void initView(){
        ExerciseWorkoutFragment fragment = new ExerciseWorkoutFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.ExerciseFragment,fragment);
        transaction.commit();


    }
}

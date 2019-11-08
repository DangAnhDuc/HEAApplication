package com.example.heaapp.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.heaapp.R;
import com.example.heaapp.model.workout.ExerciseImage.Result;
import com.example.heaapp.presenter.ExerciseImagePresenter;
import com.example.heaapp.presenter.ExerciseImagePresenterImpl;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseImageActivity extends AppCompatActivity implements ExerciseImageView {

    @BindView(R.id.exerciseImgToolbar)
    Toolbar toolbar;
    @BindView(R.id.exerImg)
    ImageView exeImg;
    List<Result> listResult;
    int exerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_image);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        exerID = bundle.getInt("exerID");

        ExerciseImagePresenter exePresneter = new ExerciseImagePresenterImpl(this,exerID);
        exePresneter.getListImage();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void getImageSuccess(List<Result> list) {
        try {
            Handler handler = new Handler();
            Thread thread = new Thread(new Runnable() {
                int i = 0;
                @Override
                public void run() {
                    Glide.with(getContext()).load(list.get(i).getImage()).error(R.drawable.wait).into(exeImg);
                    i++;
                    if(i > list.size()-1){
                        i=0;
                    }
                    handler.postDelayed(this,2000);
                }
            });
            handler.postDelayed(thread,2000);
        }catch (Exception ex){
            Glide.with(this).load(R.drawable.error_404).into(exeImg);
        }
        listResult = list;
    }
}

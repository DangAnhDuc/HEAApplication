package com.example.heaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heaapp.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseInfoActivity extends AppCompatActivity {

    @BindView(R.id.exerciseInfoToolbar)
    Toolbar exerInfoToolBar;
    @BindView(R.id.exercise_info_name)
    TextView txtExeInfoName;
    @BindView(R.id.exercise_info_description)
    TextView txtExeInfoDes;
    @BindView(R.id.exercise_info_muscle)
    TextView txtExeInfoMus;
    @BindView(R.id.btnExerInfo)
    Button btnExe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setSupportActionBar(exerInfoToolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        exerInfoToolBar.setNavigationOnClickListener(v -> finish());

        Bundle bundle = getIntent().getExtras();
        txtExeInfoName.setText(Html.fromHtml("<p>"+bundle.getString("name")+"</p>"));
        txtExeInfoDes.setText(Html.fromHtml(bundle.getString("description")));
        txtExeInfoMus.setText(Html.fromHtml("<p>"+bundle.getString("muscles")+"</p>"));

        btnExe.setOnClickListener(v-> Toast.makeText(this, "This function is developing", Toast.LENGTH_SHORT).show());
    }
}

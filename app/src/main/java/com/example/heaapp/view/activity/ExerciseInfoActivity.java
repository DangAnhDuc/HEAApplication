package com.example.heaapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.heaapp.R;
import com.example.heaapp.model.workout.Equipment.Result;
import com.example.heaapp.model.workout.Muscle.ListMuscle;
import com.example.heaapp.presenter.ExerciseInfoPresenter;
import com.example.heaapp.presenter.ExerciseInfoPresenterImpl;
import com.example.heaapp.ultis.ultis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseInfoActivity extends AppCompatActivity implements ExerciseInfoView {

    @BindView(R.id.exerciseInfoToolbar)
    Toolbar exerInfoToolBar;
    @BindView(R.id.exercise_info_name)
    TextView txtExeInfoName;
    @BindView(R.id.exercise_info_description)
    TextView txtExeInfoDes;
    @BindView(R.id.exercise_info_muscle)
    TextView txtExeInfoMus;
    @BindView(R.id.exercise_info_equipment)
    TextView txtExeInfoEquip;
    @BindView(R.id.btnExerInfo)
    Button btnExe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(exerInfoToolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        exerInfoToolBar.setNavigationOnClickListener(v -> finish());

        Bundle bundle = getIntent().getExtras();
        int exeId;
        assert bundle != null;
        ArrayList<Integer> CateID = bundle.getIntegerArrayList("muscles");
        ArrayList<Integer> EquipID = bundle.getIntegerArrayList("equipment");
        exeId = bundle.getInt("id");
        ExerciseInfoPresenter exerciseInfoPresenter = new ExerciseInfoPresenterImpl(CateID, EquipID, this);
        exerciseInfoPresenter.getListMuscle();
        exerciseInfoPresenter.getListEquipment();

        txtExeInfoName.setText(Html.fromHtml("<p>" + bundle.getString("name") + "</p>"));
        txtExeInfoDes.setText(Html.fromHtml(bundle.getString("description")));

        btnExe.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExerciseImageActivity.class);
            intent.putExtra("exerID", exeId);
            startActivity(intent);
        });
    }

    @Override
    public void getMuscleSuccess(List<ListMuscle> nameMuscle) {
        String muscle = "";
        for (int i = 0; i <= nameMuscle.size(); i++) {
            muscle += nameMuscle.get(i).getName();
            if (i < nameMuscle.size() - 1) {
                muscle += ",";
            }
            txtExeInfoMus.setText(muscle);
        }
    }

    @Override
    public void getEquipSuccess(List<Result> nameEquip) {
        String equip = "";
        for (int i = 0; i <= nameEquip.size(); i++) {
            equip += nameEquip.get(i).getName();
            if (i < nameEquip.size() - 1) {
                equip += ",";
            }
            txtExeInfoEquip.setText(equip);
        }
    }

    @Override
    public void handleErrorListMusscle(String mes) {
        ultis.showErrorMessage(getContext(), mes);
    }

    @Override
    public void handleErrorEquip(String mes) {
        ultis.showErrorMessage(getContext(), mes);
    }

    @Override
    public Context getContext() {
        return this;

    }
}

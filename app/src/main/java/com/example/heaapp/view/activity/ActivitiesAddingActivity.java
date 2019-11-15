package com.example.heaapp.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.heaapp.R;
import com.example.heaapp.presenter.ActivitiesAddingPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.ultis;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivitiesAddingActivity extends AppCompatActivity implements ActivitiesAddingView {


    @BindView(R.id.btn_addActivities)
    AppCompatButton btnAddActivities;
    ActivitiesAddingPresenterImpl activitiesAddingPresenter;
    @BindView(R.id.edt_sleeping_minutes)
    EditText edtSleepingMinutes;
    @BindView(R.id.edt_deskwork_minutes)
    EditText edtDeskworkMinutes;
    @BindView(R.id.edt_calisthenics_light_minutes)
    EditText edtCalisthenicsLightMinutes;
    @BindView(R.id.edt_calisthenics_vigorous_minutes)
    EditText edtCalisthenicsVigorousMinutes;
    @BindView(R.id.edt_gymnastics_minutes)
    EditText edtGymnasticsMinutes;
    @BindView(R.id.edt_walking_slow_minutes)
    EditText edtWalkingSlowMinutes;
    @BindView(R.id.edt_walking_normal_minutes)
    EditText edtWalkingNormalMinutes;
    @BindView(R.id.edt_walking_fast_minutes)
    EditText edtWalkingFastMinutes;
    @BindView(R.id.edt_running_slow_minutes)
    EditText edtRunningSlowMinutes;
    @BindView(R.id.edt_running_normal_minutes)
    EditText edtRunningNormalMinutes;
    @BindView(R.id.edt_running_fast_minutes)
    EditText edtRunningFastMinutes;
    @BindView(R.id.edt_cycling_slow_minutes)
    EditText edtCyclingSlowMinutes;
    @BindView(R.id.edt_cycling_fast_minutes)
    EditText edtCyclingFastMinutes;
    @BindView(R.id.edt_cycling_normal_minutes)
    EditText edtCyclingNormalMinutes;
    @BindView(R.id.edt_rope_jumping_minutes)
    EditText edtRopeJumpingMinutes;
    @BindView(R.id.edt_swimming_minutes)
    EditText edtSwimmingMinutes;
    @BindView(R.id.edt_yoga_minutes)
    EditText edtYogaMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_adding);
        ButterKnife.bind(this);
        RealmService realmService = RealmService.getInstance();
        activitiesAddingPresenter = new ActivitiesAddingPresenterImpl(this, realmService,getContext());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.btn_addActivities)
    public void onViewClicked() {
        activitiesAddingPresenter.addActivitiesListMns(edtSleepingMinutes.getText().toString(), edtDeskworkMinutes.getText().toString(), edtCalisthenicsLightMinutes.getText().toString()
                , edtCalisthenicsVigorousMinutes.getText().toString(), edtGymnasticsMinutes.getText().toString(), edtWalkingSlowMinutes.getText().toString(), edtWalkingNormalMinutes.getText().toString(), edtWalkingFastMinutes.getText().toString(),
                edtRunningSlowMinutes.getText().toString(), edtRunningNormalMinutes.getText().toString(), edtRunningFastMinutes.getText().toString(), edtCyclingSlowMinutes.getText().toString()
                , edtCyclingNormalMinutes.getText().toString(), edtCyclingFastMinutes.getText().toString(), edtRopeJumpingMinutes.getText().toString(), edtSwimmingMinutes.getText().toString(),
                edtYogaMinutes.getText().toString());
    }

    @Override
    public void addActivitiesSuccess() {
        ultis.setIntent(getContext(),HomeActivity.class);
        ultis.showMessage(getContext(),getString(R.string.add_activities_success));
    }

    @Override
    public void addActivitiesFailed() {
        ultis.showMessage(getContext(),getString(R.string.add_activities_fail));
    }
}

package com.example.heaapp.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.heaapp.R;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.user_information.CurrentUserIndices;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.presenter.DashboardPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.activity.UserInfoActivity;


public class DashBoardFragment extends BaseFragment implements DashboardView, View.OnClickListener {
    private DashboardPresenterImpl dashboardPresenter;
    private TextView tvTotalWater;
    AlertDialog alertDialog;
    private TextView tv_BMI, tv_bodyMass, tv_bodyWater, tv_waterRequired, tv_bloodVolume, tv_bodyFat, tv_FFMI, tv_DailyCal;

    @Override
    public BaseFragment provideYourFragment() {
        return new DashBoardFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dash_board, parent, false);

        //button
        Button btn75 = view.findViewById(R.id.btn_75);
        Button btn150 = view.findViewById(R.id.btn_150);
        Button btn250 = view.findViewById(R.id.btn_250);
        Button btn330 = view.findViewById(R.id.btn_330);
        Button btn_custom_water_ = view.findViewById(R.id.btn_custom_water);

        //textview
        tvTotalWater = view.findViewById(R.id.tv_total_water);

        tv_BMI = view.findViewById(R.id.tv_bmi);
        tv_bodyMass = view.findViewById(R.id.tv_leanbodymass);
        tv_bodyWater = view.findViewById(R.id.tv_bodywater);
        tv_waterRequired = view.findViewById(R.id.tv_waterreq);
        tv_bloodVolume = view.findViewById(R.id.tv_bloodVol);
        tv_bodyFat = view.findViewById(R.id.tv_bodyfat);
        tv_FFMI = view.findViewById(R.id.tv_ffmi);
        tv_DailyCal = view.findViewById(R.id.tv_dailyCal);


        RealmService realmService = RealmService.getInstance();
        dashboardPresenter = new DashboardPresenterImpl(getContext(), this, realmService);
        btn75.setOnClickListener(this);
        btn150.setOnClickListener(this);
        btn250.setOnClickListener(this);
        btn330.setOnClickListener(this);
        btn_custom_water_.setOnClickListener(this);
        dashboardPresenter.getDailySummary();
        dashboardPresenter.getCurrentUserIndices();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    dashboardPresenter.getUserInfoStatus();
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void displayDailySummary(DailySummary dailySummary) {
        tvTotalWater.setText(new StringBuilder().append("Total: ").append(String.valueOf(dailySummary.getWaterConsume())).append("ml").toString());
    }

    @Override
    public void updateWaterAmount(String waterAmount) {
        tvTotalWater.setText(new StringBuilder().append("Total: ").append(waterAmount).append("ml").toString());
    }

    @Override
    public void isUserInfoEntered(boolean isEntered) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        if (!isEntered) {
            builder.setTitle(getString(R.string.msg_request_enter_info));
            builder.setCancelable(false);
            builder.setPositiveButton("OK", (dialog, which) -> ultis.setIntent(getContext(), UserInfoActivity.class));
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    public void displayCurrentUserIndices(CurrentUserIndices currentUserIndices) {
        tv_BMI.setText(String.valueOf(currentUserIndices.getBMI()));
        tv_bodyMass.setText(String.valueOf(currentUserIndices.getBodyMass()));
        tv_bodyWater.setText(String.valueOf(currentUserIndices.getBodyWater()));
        tv_waterRequired.setText(String.valueOf(currentUserIndices.getWaterRequired()));
        tv_bloodVolume.setText(String.valueOf(currentUserIndices.getBloodVolume()));
        tv_bodyFat.setText(String.valueOf(currentUserIndices.getBodyFat()));
        tv_FFMI.setText(String.valueOf(currentUserIndices.getFFMI()));
        tv_DailyCal.setText(String.valueOf(currentUserIndices.getDailyCal()));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_75:
                dashboardPresenter.addDrunkWater(75);
                break;
            case R.id.btn_150:
                dashboardPresenter.addDrunkWater(150);
                break;
            case R.id.btn_250:
                dashboardPresenter.addDrunkWater(250);
                break;
            case R.id.btn_330:
                dashboardPresenter.addDrunkWater(330);
                break;
            case R.id.btn_custom_water:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                builder.setTitle(getString(R.string.title_input_water));

                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", (dialog, which) -> dashboardPresenter.addDrunkWater(Long.parseLong(input.getText().toString())));
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
        }
    }
}

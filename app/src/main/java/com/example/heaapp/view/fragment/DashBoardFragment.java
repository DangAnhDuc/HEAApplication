package com.example.heaapp.view.fragment;

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

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class DashBoardFragment extends BaseFragment implements DashboardView {
    @BindView(R.id.desc_kcal_eaten)
    TextView descKcalEaten;
    @BindView(R.id.desc_kcal_left)
    TextView descKcalLeft;
    @BindView(R.id.desc_kcal_burned)
    TextView descKcalBurned;
    @BindView(R.id.des_carbs)
    TextView desCarbs;
    @BindView(R.id.des_protein)
    TextView desProtein;
    @BindView(R.id.des_fat)
    TextView desFat;
    @BindView(R.id.tv_total_water)
    TextView tvTotalWater;
    @BindView(R.id.btn_75)
    Button btn75;
    @BindView(R.id.btn_150)
    Button btn150;
    @BindView(R.id.btn_250)
    Button btn250;
    @BindView(R.id.btn_330)
    Button btn330;
    @BindView(R.id.btn_custom_water)
    Button btnCustomWater;
    @BindView(R.id.tv_bmi)
    TextView tvBmi;
    @BindView(R.id.tv_leanbodymass)
    TextView tvLeanbodymass;
    @BindView(R.id.tv_bodywater)
    TextView tvBodywater;
    @BindView(R.id.tv_waterreq)
    TextView tvWaterreq;
    @BindView(R.id.tv_bloodVol)
    TextView tvBloodVol;
    @BindView(R.id.tv_bodyfat)
    TextView tvBodyfat;
    @BindView(R.id.tv_ffmi)
    TextView tvFfmi;
    @BindView(R.id.tv_dailyCal)
    TextView tvDailyCal;
    private DashboardPresenterImpl dashboardPresenter;
    private AlertDialog alertDialog;
    private Unbinder unbinder;

    @Override
    public BaseFragment provideYourFragment() {
        return new DashBoardFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dash_board, parent, false);
        unbinder= ButterKnife.bind(this,view);
        RealmService realmService = RealmService.getInstance();
        dashboardPresenter = new DashboardPresenterImpl(getContext(), this, realmService);
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
        DecimalFormat format = new DecimalFormat("0.00");
        tvBmi.setText(format.format(currentUserIndices.getBMI()));
        tvLeanbodymass.setText(format.format(currentUserIndices.getBodyMass())+" kg");
        tvBodywater.setText(format.format(currentUserIndices.getBodyWater())+ " litters");
        tvWaterreq.setText(format.format(currentUserIndices.getWaterRequired())+" litters");
        tvBloodVol.setText(format.format(currentUserIndices.getBloodVolume())+" litters");
        tvBodyfat.setText(format.format(currentUserIndices.getBodyFat())+" %");
        tvFfmi.setText(format.format(currentUserIndices.getFFMI())+ " kg/m²");
        tvDailyCal.setText(format.format(currentUserIndices.getDailyCal())+" kCal");
    }


    @OnClick({R.id.btn_75, R.id.btn_150, R.id.btn_250, R.id.btn_330, R.id.btn_custom_water})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

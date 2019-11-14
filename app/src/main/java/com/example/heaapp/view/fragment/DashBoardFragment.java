package com.example.heaapp.view.fragment;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.ActivitiesAdapter;
import com.example.heaapp.adapter.DishesAdapter;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.user_information.CurrentUserIndices;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.presenter.DashboardPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.activity.ActivitiesAddingActivity;
import com.example.heaapp.view.activity.FoodAddingActivity;
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
    @BindView(R.id.rcview_breakfast)
    RecyclerView rcviewBreakfast;
    @BindView(R.id.rcview_launch)
    RecyclerView rcviewLaunch;
    @BindView(R.id.rcview_dinner)
    RecyclerView rcviewDinner;
    @BindView(R.id.rcview_activities)
    RecyclerView rcviewActivities;

    private DashboardPresenterImpl dashboardPresenter;
    private Unbinder unbinder;

    @Override
    public BaseFragment provideYourFragment() {
        return new DashBoardFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dash_board, parent, false);
        unbinder = ButterKnife.bind(this, view);
        RealmService realmService = RealmService.getInstance();
        dashboardPresenter = new DashboardPresenterImpl(getContext(), this, realmService);
        dashboardPresenter.getDailySummary();
        dashboardPresenter.getCurrentUserIndices();
        rcviewBreakfast.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerBf = new LinearLayoutManager(getContext());
        rcviewBreakfast.setLayoutManager(layoutManagerBf);

        rcviewLaunch.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerLn = new LinearLayoutManager(getContext());
        rcviewLaunch.setLayoutManager(layoutManagerLn);

        rcviewDinner.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerDn = new LinearLayoutManager(getContext());
        rcviewDinner.setLayoutManager(layoutManagerDn);

        rcviewActivities.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerEx = new LinearLayoutManager(getContext());
        rcviewActivities.setLayoutManager(layoutManagerEx);

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
    public void onResume() {
        super.onResume();
        dashboardPresenter.getDailySummary();
    }

    @Override
    public void displayDailySummary(DailySummary dailySummary) {
        tvTotalWater.setText(String.format("Total: %sml", String.valueOf(dailySummary.getWaterConsume())));
        descKcalBurned.setText(String.valueOf(dailySummary.getBurnedCalories()));
        descKcalEaten.setText(String.valueOf(dailySummary.getEatenCalories()));
        descKcalLeft.setText(String.valueOf(dailySummary.getNeededCalories()));
        desCarbs.setText(String.format("%sg", String.valueOf(dailySummary.getEatenCarbs())));
        desProtein.setText(String.format("%sg", String.valueOf(dailySummary.getEatenProtein())));
        desFat.setText(String.format("%sg", String.valueOf(dailySummary.getEatenFat())));
        DishesAdapter dishesAdapterBf = new DishesAdapter(getContext(), dailySummary.getBreakfastDishes());
        DishesAdapter dishesAdapterLn = new DishesAdapter(getContext(), dailySummary.getLaunchDishes());
        DishesAdapter dishesAdapterDn = new DishesAdapter(getContext(), dailySummary.getDinnerDishes());
        ActivitiesAdapter activitiesAdapter = new ActivitiesAdapter(getContext(), dailySummary.getActivities());

        rcviewBreakfast.setAdapter(dishesAdapterBf);
        rcviewLaunch.setAdapter(dishesAdapterLn);
        rcviewDinner.setAdapter(dishesAdapterDn);
        rcviewActivities.setAdapter(activitiesAdapter);
    }

    @Override
    public void addDrunkWaterSuccessfully(String waterAmount) {
        tvTotalWater.setText(String.format("Total: %sml", waterAmount));

    }

    @Override
    public void isUserInfoEntered(boolean isEntered) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        if (!isEntered) {
            builder.setTitle(getString(R.string.msg_request_enter_info));
            builder.setCancelable(false);
            builder.setPositiveButton("OK", (dialog, which) -> ultis.setIntent(getContext(), UserInfoActivity.class));
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    public void displayCurrentUserIndices(CurrentUserIndices currentUserIndices) {
        DecimalFormat format = new DecimalFormat("0.00");
        tvBmi.setText(format.format(currentUserIndices.getBMI()));
        tvLeanbodymass.setText(String.format("%s kg", format.format(currentUserIndices.getBodyMass())));
        tvBodywater.setText(String.format("%s litters", format.format(currentUserIndices.getBodyWater())));
        tvWaterreq.setText(String.format("%s litters", format.format(currentUserIndices.getWaterRequired())));
        tvBloodVol.setText(String.format("%s litters", format.format(currentUserIndices.getBloodVolume())));
        tvBodyfat.setText(String.format("%s %%", format.format(currentUserIndices.getBodyFat())));
        tvFfmi.setText(String.format("%s kg/m²", format.format(currentUserIndices.getFFMI())));
        tvDailyCal.setText(String.format("%s kCal", format.format(currentUserIndices.getDailyCal())));
    }

    @Override
    public void addDrunkWaterFailed() {
            ultis.showMessage(getContext(),getString(R.string.msg_input_water_amount));
    }


    @OnClick({R.id.btn_75, R.id.btn_150, R.id.btn_250, R.id.btn_330, R.id.btn_custom_water, R.id.layout_breakfast, R.id.layout_launch, R.id.layout_dinner, R.id.layout_exercise})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_75:
                dashboardPresenter.addDrunkWater("75");
                break;
            case R.id.btn_150:
                dashboardPresenter.addDrunkWater("150");
                break;
            case R.id.btn_250:
                dashboardPresenter.addDrunkWater("250");
                break;
            case R.id.btn_330:
                dashboardPresenter.addDrunkWater("330");
                break;
            case R.id.btn_custom_water:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                builder.setTitle(getString(R.string.title_input_water));

                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", (dialog, which) -> dashboardPresenter.addDrunkWater(input.getText().toString()));
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
                break;
            case R.id.layout_breakfast:
                Intent intentBreakfast = new Intent(getContext(), FoodAddingActivity.class);
                intentBreakfast.putExtra("FoodTime", "Breakfast");
                startActivity(intentBreakfast);
                break;
            case R.id.layout_launch:
                Intent intentLaunch = new Intent(getContext(), FoodAddingActivity.class);
                intentLaunch.putExtra("FoodTime", "Launch");
                startActivity(intentLaunch);
                break;
            case R.id.layout_dinner:
                Intent intentDinner = new Intent(getContext(), FoodAddingActivity.class);
                intentDinner.putExtra("FoodTime", "Dinner");
                startActivity(intentDinner);
                break;
            case R.id.layout_exercise:
                ultis.setIntent(getContext(), ActivitiesAddingActivity.class);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}

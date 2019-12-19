package com.example.heaapp.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.ActivitiesAdapter;
import com.example.heaapp.adapter.SpinnerAdapter;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.user_information.CurrentUserIndices;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.presenter.DashboardPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.activity.CurrentUserDetailActivity;
import com.example.heaapp.view.activity.FoodAddingActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

import static android.content.Context.MODE_PRIVATE;


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
    @BindView(R.id.rcview_activities)
    RecyclerView rcviewActivities;
    @BindView(R.id.cardView_index)
    CardView cardViewIndex;
    @BindView(R.id.cardView_water)
    CardView cardViewWater;
    @BindView(R.id.cardView_food)
    CardView cardViewFood;
    @BindView(R.id.cardView_activity)
    CardView cardViewActivity;
    @BindView(R.id.cardView_boyIndices)
    CardView cardViewBoyIndices;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.layout_food)
    RelativeLayout layoutFood;
    @BindView(R.id.layout_exercise)
    RelativeLayout layoutExercise;


    private DashboardPresenterImpl dashboardPresenter;
    private Unbinder unbinder;
    private int[] icons = {R.drawable.sleeping, R.drawable.deskwork, R.drawable.callisthenics, R.drawable.callisthenics,
            R.drawable.gymastics, R.drawable.walking, R.drawable.walking, R.drawable.walking, R.drawable.running, R.drawable.running,
            R.drawable.running, R.drawable.bicycle, R.drawable.bicycle, R.drawable.bicycle, R.drawable.jumping,
            R.drawable.swimming, R.drawable.yoga};
    private int activityPosition = 0;
    private List<Double> METs = new ArrayList<>();
    private long activityBurnedEnergy = 0;
    private Dialog addActivityDialog;

    private GuideView guideView;
    private GuideView.Builder guideViewBuilder;


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

        rcviewActivities.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerEx = new LinearLayoutManager(getContext());
        rcviewActivities.setLayoutManager(layoutManagerEx);
        setClickable(false);
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

    private void setClickable(boolean clickable) {
        scrollView.setClickable(clickable);
        btn75.setClickable(clickable);
        btn150.setClickable(clickable);
        btn250.setClickable(clickable);
        btn330.setClickable(clickable);
        btnCustomWater.setClickable(clickable);
        layoutFood.setClickable(clickable);
        layoutExercise.setClickable(clickable);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        dashboardPresenter.getDailySummary();
    }

    private void loadGuideTour() {
        createGuideView();
        saveTourGuidePrefsData();
    }

    private void createGuideView() {
        guideViewBuilder = new GuideView.Builder(getContext())
                .setTitle("Guide Title Text")
                .setContentText("Guide Description Text\n .....Guide Description Text\n .....Guide Description Text .....")
                .setGravity(Gravity.center)
                .setDismissType(DismissType.outside)
                .setTargetView(cardViewIndex)
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        switch (view.getId()) {
                            case R.id.cardView_index:
                                guideViewBuilder.setTitle("acsad");
                                guideViewBuilder.setContentText("asdaskjaskgjf");
                                guideViewBuilder.setTargetView(cardViewWater).build();
                                break;
                            case R.id.cardView_water:
                                guideViewBuilder.setTargetView(cardViewFood).build();
                                break;
                            case R.id.cardView_food:
                                scrollView.scrollTo(0, cardViewActivity.getTop());
                                guideViewBuilder.setTargetView(cardViewActivity).build();
                                break;
                            case R.id.cardView_activity:
                                guideViewBuilder.setTargetView(cardViewBoyIndices).build();
                                break;
                            case R.id.cardView_boyIndices:
                                return;
                        }
                        guideView = guideViewBuilder.build();
                        guideView.show();
                    }
                });

        guideView = guideViewBuilder.build();
        guideView.show();
        updatingForDynamicLocationViews();
    }


    private void updatingForDynamicLocationViews() {
        cardViewActivity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                guideView.updateGuideViewLocation();
            }
        });
    }

    private boolean restoreTourGuidePrefsData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("tourGuideRefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isDashboardGuideOpened", false);
    }


    private void saveTourGuidePrefsData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("tourGuideRefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDashboardGuideOpened", true);
        editor.apply();
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
        ActivitiesAdapter activitiesAdapter = new ActivitiesAdapter(getContext(), dailySummary.getActivities());
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
            builder.setPositiveButton("OK", (dialog, which) -> ultis.setIntent(getContext(), CurrentUserDetailActivity.class));
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            if (!restoreTourGuidePrefsData()) {
                loadGuideTour();
            }
        }
    }

    @Override
    public void displayCurrentUserIndices(CurrentUserIndices currentUserIndices) {
        DecimalFormat format = new DecimalFormat("0.00");
        tvBmi.setText(format.format(currentUserIndices.getBMI()));
        tvLeanbodymass.setText(String.format("%s kg", format.format(currentUserIndices.getBodyMass())));
        tvBodywater.setText(String.format("%s %s", format.format(currentUserIndices.getBodyWater()), getString(R.string.litter)));
        tvWaterreq.setText(String.format("%s %s", format.format(currentUserIndices.getWaterRequired()), getString(R.string.litter)));
        tvBloodVol.setText(String.format("%s %s", format.format(currentUserIndices.getBloodVolume()), getString(R.string.litter)));
        tvBodyfat.setText(String.format("%s %%", format.format(currentUserIndices.getBodyFat())));
        tvFfmi.setText(String.format("%s kg/m²", format.format(currentUserIndices.getFFMI())));
        tvDailyCal.setText(String.format("%s kCal", format.format(currentUserIndices.getDailyCal())));
    }

    @Override
    public void addDrunkWaterFailed() {
        ultis.showErrorMessage(getContext(), getString(R.string.msg_input_water_amount));
    }

    @Override
    public void addActivitiesSuccess() {
        ultis.showSuccessMessage(getContext(), getString(R.string.add_activities_success));
        addActivityDialog.dismiss();
        dashboardPresenter.getDailySummary();
    }

    @Override
    public void addActivitiesFailed() {
        ultis.showErrorMessage(getContext(), getString(R.string.add_activities_fail));
        addActivityDialog.dismiss();
    }


    @OnClick({R.id.btn_75, R.id.btn_150, R.id.btn_250, R.id.btn_330, R.id.btn_custom_water, R.id.layout_food, R.id.layout_exercise})
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
                input.setTextColor(Color.WHITE);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", (dialog, which) -> dashboardPresenter.addDrunkWater(input.getText().toString()));
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
                break;
            case R.id.layout_food:
                Intent intentBreakfast = new Intent(getContext(), FoodAddingActivity.class);
                intentBreakfast.putExtra("FoodTime", "Breakfast");
                startActivity(intentBreakfast);
                break;
            case R.id.layout_exercise:
                Double[] arrayMET = {0.95, 1.5, 3.8, 8.0, 3.8, 2.0, 3.3, 4.3, 5.0, 10.5, 23.0, 3.5, 8.0, 15.8, 8.8, 9.5, 8.8};
                METs = Arrays.asList(arrayMET);
                addActivityDialog = new Dialog(getContext(), R.style.AlertDialogTheme);
                addActivityDialog.setContentView(R.layout.activity_custom_dialog);
                addActivityDialog.setTitle("Add activity!");

                Spinner activitySpinner = addActivityDialog.findViewById(R.id.activity_spinner);
                TextView tvActivityEnergy = addActivityDialog.findViewById(R.id.activity_burned_energy);
                EditText edtActivityTimeDuration = addActivityDialog.findViewById(R.id.edt_activity_duration);
                Button btnAddActivity = addActivityDialog.findViewById(R.id.btn_addActivity);

                activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        activityPosition = position;
                        try {
                            activityBurnedEnergy = Double.valueOf(dashboardPresenter.calculateBurnedEnergy(METs.get(activityPosition), edtActivityTimeDuration.getText().toString())).longValue();
                            tvActivityEnergy.setText(String.format("Total: %dkCal", activityBurnedEnergy));
                        } catch (Exception e) {
                            tvActivityEnergy.setText(getString(R.string.default_activity_burned_energy));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(), icons, getContext().getResources().getStringArray(R.array.activitiesName));
                activitySpinner.setAdapter(spinnerAdapter);
                edtActivityTimeDuration.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        try {
                            activityBurnedEnergy = Double.valueOf(dashboardPresenter.calculateBurnedEnergy(METs.get(activityPosition), edtActivityTimeDuration.getText().toString())).longValue();
                            tvActivityEnergy.setText(String.format("Total: %dkCal", activityBurnedEnergy));
                        } catch (Exception e) {
                            tvActivityEnergy.setText(getString(R.string.default_activity_burned_energy));
                        }

                    }
                });
                btnAddActivity.setOnClickListener(v -> {
                    if (activityBurnedEnergy != 0) {
                        dashboardPresenter.addActivity(getContext().getResources().getStringArray(R.array.activitiesName)[activityPosition],
                                edtActivityTimeDuration.getText().toString(), activityBurnedEnergy);
                    } else {
                        ultis.showErrorMessage(getContext(), getString(R.string.msg_input_valid_time));
                    }
                });
                addActivityDialog.show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

}

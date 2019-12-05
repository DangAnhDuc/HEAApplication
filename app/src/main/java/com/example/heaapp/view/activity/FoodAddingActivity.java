package com.example.heaapp.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.heaapp.Helper.MySwipeHelper;
import com.example.heaapp.R;
import com.example.heaapp.adapter.FoodAdapter;
import com.example.heaapp.model.food.Data;
import com.example.heaapp.presenter.FoodAddingPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.ultis;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodAddingActivity extends AppCompatActivity implements FoodAddingView {

    @BindView(R.id.foodRecyclerView)
    RecyclerView foodRecyclerView;
    @BindView(R.id.edt_foodName)
    EditText edtFoodName;
    @BindView(R.id.edt_foodEnergy)
    EditText edtFoodEnergy;
    @BindView(R.id.btn_addFood)
    AppCompatButton btnAddFood;
    FoodAddingPresenterImpl foodAddingPresenter;
    BottomSheetBehavior bottomSheetBehavior;
    @BindView(R.id.toggleButton)
    ToggleButton toggleButton;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;
    @BindView(R.id.edt_foodCarbs)
    EditText edtFoodCarbs;
    @BindView(R.id.edt_foodProtein)
    EditText edtFoodProtein;
    @BindView(R.id.edt_foodFat)
    EditText edtFoodFat;

    String foodTime;
    @BindView(R.id.foodAddingToolbar)
    Toolbar foodAddingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_adding);
        ButterKnife.bind(this);

        //set toolbar
        setSupportActionBar(foodAddingToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        foodAddingToolbar.setNavigationOnClickListener(v -> finish());

        RealmService realmService = RealmService.getInstance();
        foodAddingPresenter = new FoodAddingPresenterImpl(this, getContext(), realmService);
        foodRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        foodRecyclerView.setLayoutManager(layoutManager);
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        foodTime = extras.getString("FoodTime");
        toggleButton = findViewById(R.id.toggleButton);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_EXPANDED) {
                    toggleButton.setChecked(true);
                } else if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    toggleButton.setChecked(false);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
        foodAddingPresenter.crawlFoodData();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void crawlDataFailed(String message) {

    }

    @Override
    public void crawlDataSuccess(List<Data> foodList) {
        FoodAdapter foodAdapter = new FoodAdapter(getContext(), foodList);
        foodRecyclerView.setAdapter(foodAdapter);
        MySwipeHelper swipeHelper = new MySwipeHelper(this, foodRecyclerView, 200) {
            @Override
            protected void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                buffer.add(new MyButton(FoodAddingActivity.this,
                        "ADD", 30, 0, Color.parseColor("#0E9577"),
                        pos -> foodAddingPresenter.addDishes(foodList.get(pos), foodTime)));
            }
        };

    }

    @Override
    public void addFoodSuccessfully() {
        ultis.showSuccessMessage(getContext(), getString(R.string.add_food_success));
    }

    @Override
    public void addFoodFailed() {
        ultis.showErrorMessage(getContext(), getString(R.string.add_food_fail));
    }

    @OnClick(R.id.btn_addFood)
    public void onViewClicked() {
        foodAddingPresenter.addDishesCustom(foodTime, edtFoodName.getText().toString(), edtFoodEnergy.getText().toString(),
                edtFoodCarbs.getText().toString(), edtFoodProtein.getText().toString(), edtFoodFat.getText().toString());
    }
}

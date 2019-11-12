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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.Helper.MySwipeHelper;
import com.example.heaapp.R;
import com.example.heaapp.adapter.FoodAdapter;
import com.example.heaapp.callback.MyButtonClickListener;
import com.example.heaapp.model.food.Data;
import com.example.heaapp.presenter.FoodAddingPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.ultis;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_adding);
        ButterKnife.bind(this);
        RealmService realmService = RealmService.getInstance();
        foodAddingPresenter = new FoodAddingPresenterImpl(this, getContext(), realmService);
        foodRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        foodRecyclerView.setLayoutManager(layoutManager);
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
        MySwipeHelper swipeHelper = new MySwipeHelper(this, foodRecyclerView, 200) {
            @Override
            protected void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MySwipeHelper.MyButton> buffer) {
                buffer.add(new MyButton(FoodAddingActivity.this,
                        "ADD", 30, 0, Color.parseColor("#0E9577"),
                        new MyButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                ultis.showMessage(getContext(), "add clicked");
                            }
                        }));
            }
        };
        FoodAdapter foodAdapter = new FoodAdapter(getContext(), foodList);
        foodRecyclerView.setAdapter(foodAdapter);

    }
}

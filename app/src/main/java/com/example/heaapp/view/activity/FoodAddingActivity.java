package com.example.heaapp.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.model.food.Data;
import com.example.heaapp.presenter.FoodAddingPresenterImpl;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_adding);
        ButterKnife.bind(this);

        foodAddingPresenter=new FoodAddingPresenterImpl(this,getContext());
        foodAddingPresenter.crawlFoodData();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        foodAddingPresenter.disposeApi();
    }

    @Override
    public void crawlDataFailed(String message) {

    }

    @Override
    public void crawlDataSuccess(List<Data> informationList) {
        Log.d("data",informationList.toString());
    }
}

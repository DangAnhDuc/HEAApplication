package com.example.heaapp.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.heaapp.R;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.presenter.DashboardPresenterImpl;
import com.example.heaapp.service.RealmService;

import io.realm.Realm;


public class DashBoardFragment extends BaseFragment implements DashboardView, View.OnClickListener {
    private DashboardPresenterImpl dashboardPresenter;
    private TextView tvTotalWater;

    @Override
    public BaseFragment provideYourFragment() {
        return new DashBoardFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dash_board,parent,false);
        Button btn75 = (Button) view.findViewById(R.id.btn_75);
        Button btn150 = (Button) view.findViewById(R.id.btn_150);
        Button btn250 = (Button) view.findViewById(R.id.btn_250);
        Button btn330 = (Button) view.findViewById(R.id.btn_330);
        Button btn_custom_water_=(Button) view.findViewById(R.id.btn_custom_water);
        tvTotalWater=(TextView) view.findViewById(R.id.tv_total_water);
        RealmService realmService = RealmService.getInstance();
        dashboardPresenter= new DashboardPresenterImpl(this, realmService);
        btn75.setOnClickListener(this);
        btn150.setOnClickListener(this);
        btn250.setOnClickListener(this);
        btn330.setOnClickListener(this);
        btn_custom_water_.setOnClickListener(this);
        dashboardPresenter.getDailySummary();
        return view;
    }


    @Override
    public void dispayDailySummary(DailySummary dailySummary) {
        tvTotalWater.setText(new StringBuilder().append("Total: ").append(String.valueOf(dailySummary.getWaterConsume())).append("ml").toString());
    }

    @Override
    public void updateWaterAmount(String waterAmount) {
            tvTotalWater.setText(new StringBuilder().append("Total: ").append(waterAmount).append("ml").toString());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                builder.setTitle("Input water amount");

                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dashboardPresenter.addDrunkWater(Long.parseLong(input.getText().toString()));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
        }
    }
}

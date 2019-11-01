package com.example.heaapp.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.heaapp.R;
import com.example.heaapp.presenter.UserInfoPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.ultis;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoActivity extends AppCompatActivity implements UserInforView {

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_age)
    TextInputEditText edtAge;
    @BindView(R.id.radioSexGrp)
    RadioGroup radioSexGrp;
    @BindView(R.id.edt_weight)
    TextInputEditText edtWeight;
    @BindView(R.id.edt_height)
    TextInputEditText edtHeight;
    @BindView(R.id.edt_waist)
    TextInputEditText edtWaist;
    @BindView(R.id.edt_Hip)
    TextInputEditText edtHip;
    @BindView(R.id.edt_chest)
    TextInputEditText edtChest;
    @BindView(R.id.btn_saveInfo)
    AppCompatButton btnSaveInfo;

    RadioButton radioButton;
    UserInfoPresenterImpl userInfoPresenter;
    @BindView(R.id.radioM)
    RadioButton radioM;
    @BindView(R.id.radioF)
    RadioButton radioF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        RealmService realmService=RealmService.getInstance();
        userInfoPresenter = new UserInfoPresenterImpl(this, getContext(),realmService);

        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioSexGrp.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                userInfoPresenter.saveInfo(edtAge.getText().toString(), radioButton.getText().toString(), edtWeight.getText().toString(),
                        edtHeight.getText().toString(), edtWaist.getText().toString(), edtHip.getText().toString(), edtChest.getText().toString());
            }
        });
        userInfoPresenter.loadInfo();
    }

    @Override
    public void onSaveInfoSuccess() {
        ultis.showMessage(getContext(), "Save information successfully!");
        ultis.setIntent(getContext(), HomeActivity.class);
    }

    @Override
    public void onSaveInfoFail(String error) {
        ultis.showMessage(getContext(), error);
    }

    @Override
    public void displayInfo(int age, String sex, long weight, long height, long waist, long hip, long chest) {
        edtAge.setText(String.valueOf(age));
        if(sex.equals("Male")){
            radioM.setChecked(true);
        }
        else {
            radioF.setChecked(true);
        }
        edtWeight.setText(String.valueOf(weight));
        edtHeight.setText(String.valueOf(height));
        edtWaist.setText(String.valueOf(waist));
        edtHip.setText(String.valueOf(hip));
        edtChest.setText(String.valueOf(chest));
    }

    @Override
    public void displayErrorMessage(String message) {
        ultis.showMessage(getContext(), message);
    }

    @Override
    public Context getContext() {
        return this;
    }
}

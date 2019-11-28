package com.example.heaapp.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.heaapp.R;
import com.example.heaapp.presenter.UserInfoPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.ultis.ultis;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class UserInfoActivity extends AppCompatActivity implements UserInfoView {

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
    @BindView(R.id.img_user_ava)
    CircleImageView imgUserAva;
    private AlertDialog progressDialog;
    private static final int IMAGE_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        RealmService realmService = RealmService.getInstance();
        userInfoPresenter = new UserInfoPresenterImpl(this, getContext(), realmService);
        progressDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage(getString(R.string.uploading))
                .setTheme(R.style.SpotsDialog)
                .setCancelable(false).build();
        userInfoPresenter.loadInfo();
        btnSaveInfo.setOnClickListener(v -> {
            int selectedId = radioSexGrp.getCheckedRadioButtonId();
            radioButton = findViewById(selectedId);
            userInfoPresenter.saveInfo(edtAge.getText().toString(), radioButton.getText().toString(), edtWeight.getText().toString(),
                    edtHeight.getText().toString(), edtWaist.getText().toString(), edtHip.getText().toString(), edtChest.getText().toString());
        });
    }

    @Override
    public void onSaveInfoSuccess() {
        ultis.showMessage(getContext(), getString(R.string.msg_save_info_success));
        ultis.setIntent(getContext(), HomeActivity.class);
    }

    @Override
    public void onSaveInfoFail(String error) {
        ultis.showMessage(getContext(), error);
    }

    @Override
    public void displayInfo(String name,String imgURL, int age, String sex, long weight, long height, long waist, long hip, long chest) {
        edtName.setEnabled(false);
        edtName.setText(name);
        if(imgURL.equals("default")){
            Glide.with(getContext()).load(R.drawable.default_ava).into(imgUserAva);
        }
        else {
            Glide.with(getContext()).load(imgURL).into(imgUserAva);

        }
        edtAge.setText(String.valueOf(age));
        if (sex.equals("Male")) {
            radioM.setChecked(true);
        } else {
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
    public void updateUserImage(String imageUri) {
        Glide.with(getContext()).load(imageUri).into(imgUserAva);
        progressDialog.dismiss();
    }

    @Override
    public void displayUploadFailed() {
        ultis.showMessage(getContext(), getString(R.string.msg_upload_failed));
    }

    @Override
    public void displayUploadError(String message) {
        ultis.showMessage(getContext(), message);
        progressDialog.dismiss();
    }

    @Override
    public void displayNoImageSelected() {
        ultis.showMessage(getContext(), getString(R.string.msg_no_image_selected));

    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.img_user_ava)
    public void onViewClicked() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== IMAGE_REQUEST&& resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            Common.userImageUri = data.getData();
            if (Common.uploadUserImageTask != null && Common.uploadUserImageTask.isInProgress()) {
                ultis.showMessage(getContext(), getString(R.string.msg_upload_in_progress));
            } else {
                userInfoPresenter.uploadImageFromGallery();
            }
        }
    }

}

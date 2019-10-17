package com.example.heaapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.heaapp.R;
import com.example.heaapp.presenter.SignUpPresenter;
import com.example.heaapp.presenter.SignUpPresenterImpl;
import com.example.heaapp.ultis.ultis;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_signup)
    AppCompatButton btnSignup;
    @BindView(R.id.link_login)
    TextView linkLogin;

    FirebaseAuth firebaseAuth;
    SignUpPresenterImpl signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        firebaseAuth=FirebaseAuth.getInstance();
        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ultis.setIntent(SignUpActivity.this, LoginActivity.class);
                finish();
            }
        });


        signUpPresenter= new SignUpPresenterImpl(firebaseAuth);
        signUpPresenter.attachView(this);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpPresenter.signUp(edtName.getText().toString().trim(),edtEmail.getText().toString().trim(),edtPassword.getText().toString().trim());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        signUpPresenter.detachView();
    }

    @Override
    public void showValidationError() {
        ultis.showMessage(this,"Please, Check email and password");
    }

    @Override
    public void signUpSuccess() {
        ultis.showMessage(this,"Sign Up Successfully!");
        ultis.setIntent(this,LoginActivity.class);
    }

    @Override
    public void signUpError() {
        ultis.showMessage(this,"Sign Up Failed");
    }

    @Override
    public Context getContext() {
        return this;
    }
}

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
import com.example.heaapp.presenter.LoginPresenterImpl;
import com.example.heaapp.ultis.ultis;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.link_signup)
    TextView linkSignup;

    FirebaseAuth firebaseAuth;
    LoginPresenterImpl loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        firebaseAuth= FirebaseAuth.getInstance();
        loginPresenter= new LoginPresenterImpl(firebaseAuth);

        loginPresenter.attachView(this);
        loginPresenter.checkLogin();

        linkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ultis.setIntent(LoginActivity.this, SignUpActivity.class);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.login(edtEmail.getText().toString().trim(),edtPassword.getText().toString().trim());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }

    @Override
    public void showValidationError(String message) {
        ultis.showMessage(this,message);
    }

    @Override
    public void loginSuccess() {
        ultis.showMessage(this,"Login Successfully!");
        ultis.setIntent(this,MainActivity.class);
    }

    @Override
    public void loginError() {
        ultis.showMessage(this,"Login Failed!");
    }

    @Override
    public void isLogin(boolean isLogin) {
        if(isLogin){
            ultis.setIntent(this,MainActivity.class);
            finish();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}

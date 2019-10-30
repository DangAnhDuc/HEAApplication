package com.example.heaapp.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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
    ProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;

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
        progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        loginPresenter.login(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim());
                        return true;
                    }
                }
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }

    @Override
    public void showPasswordError() {
        edtPassword.setError("Password must be at least 6 characters!");
    }

    @Override
    public void showEmailError() {
        edtEmail.setError("Invalid email address!");
    }

    @Override
    public void setProgressVisibility(boolean visibility) {
        if(visibility){
            btnLogin.setEnabled(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
        }
        else {
            progressDialog.dismiss();
            btnLogin.setEnabled(true);

        }
    }

    @Override
    public void showValidationError(String message) {
        ultis.showMessage(this,message);
    }

    @Override
    public void loginSuccess() {
        ultis.showMessage(this,"Login Successfully!");
        ultis.setIntent(this, HomeActivity.class);
    }

    @Override
    public void loginError() {
        ultis.showMessage(this,"Login Failed!");
    }

    @Override
    public void isLogin(boolean isLogin) {
        if(isLogin){
            ultis.setIntent(this, HomeActivity.class);
            finish();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        ultis.showMessage(this, "Press once again to exit!");
    }

}

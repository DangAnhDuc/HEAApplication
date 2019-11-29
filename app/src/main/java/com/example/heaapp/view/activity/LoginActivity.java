package com.example.heaapp.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.heaapp.R;
import com.example.heaapp.presenter.LoginPresenterImpl;
import com.example.heaapp.ultis.ultis;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.link_signup)
    TextView linkSignup;

    LoginPresenterImpl loginPresenter;
    AlertDialog loginProgressDialog;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenterImpl(getContext());
        loginPresenter.attachView(this);
        loginPresenter.checkLogin();

        linkSignup.setOnClickListener(v -> ultis.setIntent(LoginActivity.this, SignUpActivity.class));

        btnLogin.setOnClickListener(v -> loginPresenter.login(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim()));
        loginProgressDialog = new SpotsDialog.Builder()
                .setMessage(getString(R.string.msg_authenticating))
                .setContext(this)
                .setTheme(R.style.SpotsDialog)
                .setCancelable(false).build();

        edtPassword.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    loginPresenter.login(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim());
                    return true;
                }
            }
            return false;
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }

    @Override
    public void showPasswordError() {
        edtPassword.setError(getString(R.string.msg_password_policy));
    }

    @Override
    public void showEmailError() {
        edtEmail.setError(getString(R.string.msg_invalid_email));
    }

    @Override
    public void setProgressVisibility(boolean visibility) {
        if (visibility) {
            loginProgressDialog.setMessage(getString(R.string.msg_authenticating));
            loginProgressDialog.show();
        } else {
            loginProgressDialog.dismiss();

        }
    }

    @Override
    public void showValidationError(String message) {
        ultis.showMessage(this, message);
    }

    @Override
    public void loginSuccess() {
        ultis.showMessage(this, getString(R.string.msg_login_success));
        ultis.setIntent(this, HomeActivity.class);
    }

    @Override
    public void loginError() {
        ultis.showMessage(this, getString(R.string.msg_login_failed));
    }

    @Override
    public void isLogin(boolean isLogin) {
        if (isLogin) {
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
        ultis.showMessage(this, getString(R.string.msg_press_to_exit));
    }

}

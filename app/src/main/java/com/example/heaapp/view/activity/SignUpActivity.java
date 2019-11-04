package com.example.heaapp.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.heaapp.R;
import com.example.heaapp.presenter.SignUpPresenterImpl;
import com.example.heaapp.ultis.ultis;

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

    SignUpPresenterImpl signUpPresenter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        linkLogin.setOnClickListener(v -> {
            ultis.setIntent(SignUpActivity.this, LoginActivity.class);
            finish();
        });


        signUpPresenter = new SignUpPresenterImpl(getContext());
        signUpPresenter.attachView(this);

        btnSignup.setOnClickListener(v -> signUpPresenter.signUp(edtName.getText().toString().trim(), edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim()));
        progressDialog = new ProgressDialog(SignUpActivity.this, R.style.AppTheme_Dark_Dialog);

        edtPassword.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    signUpPresenter.signUp(edtName.getText().toString().trim(), edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim());
                    return true;
                }
            }
            return false;
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        signUpPresenter.detachView();
    }

    @Override
    public void setProgressVisibility(boolean visibility) {
        if (visibility) {
            btnSignup.setEnabled(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getString(R.string.msg_create_account));
            progressDialog.show();
        } else {
            btnSignup.setEnabled(true);
            progressDialog.dismiss();
        }
    }

    @Override
    public void showNameError() {
        edtName.setError(getString(R.string.msg_username_policy));
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
    public void showValidationError(String message) {
        ultis.showMessage(this, message);
    }

    @Override
    public void signUpSuccess() {
        ultis.showMessage(this, getString(R.string.msg_signup_success));
        ultis.setIntent(this, LoginActivity.class);
    }

    @Override
    public void signUpError() {
        ultis.showMessage(this, getString(R.string.msg_signup_failed));
    }

    @Override
    public Context getContext() {
        return this;
    }
}

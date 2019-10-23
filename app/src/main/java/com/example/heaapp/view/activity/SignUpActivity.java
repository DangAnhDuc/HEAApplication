package com.example.heaapp.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.heaapp.R;
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
    ProgressDialog progressDialog;
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
        progressDialog = new ProgressDialog(SignUpActivity.this,R.style.AppTheme_Dark_Dialog);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        signUpPresenter.signUp(edtName.getText().toString().trim(), edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim());
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
        signUpPresenter.detachView();
    }

    @Override
    public void setProgressVisibility(boolean visibility) {
        if(visibility){
            btnSignup.setEnabled(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();
        }
        else {
            btnSignup.setEnabled(true);
            progressDialog.dismiss();
        }
    }

    @Override
    public void showNameError() {
        edtName.setError("Name must be at least 3 characters!");
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
    public void showValidationError(String message) {
        ultis.showMessage(this,message);
    }

    @Override
    public void signUpSuccess() {
        ultis.showMessage(this,"Sign Up Successfully!");
        ultis.setIntent(this,LoginActivity.class);
    }

    @Override
    public void signUpError() {
        ultis.showMessage(this,"Sign Up Failed!");
    }

    @Override
    public Context getContext() {
        return this;
    }
}

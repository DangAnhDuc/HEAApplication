package com.example.heaapp.presenter;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.heaapp.view.activity.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenterImpl implements LoginPresenter {

    private FirebaseAuth firebaseAuth;
    private LoginView loginView;

    public LoginPresenterImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void login(String email, String password) {
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            loginView.showValidationError("Email and password can't be empty");
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginView.showEmailError();
        }
        else if(password.length()<6){
            loginView.showPasswordError();
        }
        else {
            loginView.setProgressVisibility(true);

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) loginView, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                loginView.setProgressVisibility(false);
                                loginView.loginError();
                            } else {
                                loginView.loginSuccess();
                            }
                        }
                    });
        }
    }

    @Override
    public void checkLogin() {
        if(firebaseAuth.getCurrentUser()!=null){
            loginView.isLogin(true);
        }
        else {
            loginView.isLogin(false);
        }
    }

    @Override
    public void attachView(LoginView view) {
        loginView= view;
    }

    @Override
    public void detachView() {
        loginView=null;
    }
}

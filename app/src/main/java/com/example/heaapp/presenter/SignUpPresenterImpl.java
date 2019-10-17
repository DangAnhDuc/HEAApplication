package com.example.heaapp.presenter;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.heaapp.view.activity.SignUpView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPresenterImpl implements SignUpPresenter {
    private SignUpView signUpView;
    private FirebaseAuth firebaseAuth;
    public SignUpPresenterImpl(FirebaseAuth auth) {
        this.firebaseAuth = auth;
    }

    @Override
    public void signUp(String name, String email, String password) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            signUpView.showValidationError();
        }
        else {
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener((Activity) signUpView, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                signUpView.signUpError();
                            }
                            else {
                                signUpView.signUpSuccess();
                            }
                        }
                    });
        }
    }

    @Override
    public void attachView(SignUpView view) {
        signUpView=view;
    }

    @Override
    public void detachView() {
        signUpView=null;
    }
}

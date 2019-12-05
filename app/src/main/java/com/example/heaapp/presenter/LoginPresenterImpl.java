package com.example.heaapp.presenter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.heaapp.R;
import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.User;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.view.activity.LoginView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginPresenterImpl implements LoginPresenter, OnTransactionCallback {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private LoginView loginView;
    private Context context;

    public LoginPresenterImpl(Context context) {
        this.context = context;
    }

    //login to account
    @Override
    public void login(String email, String password) {
        //check input condition
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            loginView.showValidationError(context.getString(R.string.msg_emai_pw_empty));
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginView.showEmailError();
        } else if (password.length() < 6) {
            loginView.showPasswordError();
        } else {
            loginView.setProgressVisibility(true);

            //login to firebase account
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) loginView, task -> {
                        if (!task.isSuccessful()) {
                            loginView.setProgressVisibility(false);
                            loginView.loginError();
                        } else {
                            loginView.loginSuccess();
                        }
                    });
        }
    }

    //check if user have logined
    @Override
    public void checkLogin() {
        if (firebaseAuth.getCurrentUser() != null) {
            loginView.isLogin(true);
        } else {
            loginView.isLogin(false);
        }
    }

    @Override
    public void attachView(LoginView view) {
        loginView = view;
    }

    @Override
    public void detachView() {
        loginView = null;
    }

    @Override
    public void onTransactionSuccess() {

    }

    @Override
    public void onTransactionError(Exception e) {

    }
}

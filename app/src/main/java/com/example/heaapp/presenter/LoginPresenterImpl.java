package com.example.heaapp.presenter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.heaapp.R;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.view.activity.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPresenterImpl implements LoginPresenter {

    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private LoginView loginView;
    private Context context;
    public LoginPresenterImpl(Context context) {
        this.context=context;
    }

    //login to account
    @Override
    public void login(String email, String password) {
        //check input condition
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            loginView.showValidationError(context.getString(R.string.msg_emai_pw_empty));
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginView.showEmailError();
        }
        else if(password.length()<6){
            loginView.showPasswordError();
        }
        else {
            loginView.setProgressVisibility(true);

            //login to firebase account
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) loginView, task -> {
                        if(!task.isSuccessful()) {
                            loginView.setProgressVisibility(false);
                            loginView.loginError();
                        } else {
                            loginView.loginSuccess();
                            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            databaseReference.child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Common.name =dataSnapshot.getValue(String.class);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    });
        }
    }

    //check if user have logined
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

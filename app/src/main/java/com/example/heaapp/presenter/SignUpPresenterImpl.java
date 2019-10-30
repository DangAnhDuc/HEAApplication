package com.example.heaapp.presenter;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.heaapp.view.activity.SignUpView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpPresenterImpl implements SignUpPresenter {
    private SignUpView signUpView;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    public SignUpPresenterImpl(FirebaseAuth auth) {
        this.firebaseAuth = auth;
    }

    @Override
    public void signUp(String name, String email, String password) {
        //check input condition
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            signUpView.showValidationError("All fields must not be empty!");
        }
        else if(name.length()<3){
            signUpView.showNameError();
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpView.showEmailError();
        }
        else if(password.length()<6){
            signUpView.showPasswordError();
        }
        else {
            signUpView.setProgressVisibility(true);
            //create user and add to firebase database
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener((Activity) signUpView, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            signUpView.setProgressVisibility(false);
                            if(!task.isSuccessful()){
                                signUpView.signUpError();
                            }
                            else {
                                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                                String userId= firebaseUser.getUid();

                                databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                                HashMap<String,String> hashMap=new HashMap<>();
                                hashMap.put("id",userId);
                                hashMap.put("username",name);
                                hashMap.put("imageURl","default");

                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            signUpView.signUpSuccess();
                                        }
                                    }
                                });

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

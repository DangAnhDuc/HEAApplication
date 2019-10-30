package com.example.heaapp.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.activity.HomeView;
import com.example.heaapp.view.activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePresenterImpl implements HomePresenter {
    private FirebaseAuth firebaseAuth;
    private HomeView homeView;
    private FirebaseAuth.AuthStateListener authListener;
    private Context context;


    public HomePresenterImpl(FirebaseAuth firebaseAuth, final Context context) {
        this.firebaseAuth = firebaseAuth;
        this.context = context;

        //check user signout
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    ultis.setIntent(context, LoginActivity.class);
                }
            }
        };
    }

    @Override
    public void getCurrentUser() {
        if (firebaseAuth.getCurrentUser()!= null) {
            homeView.setUser(firebaseAuth.getCurrentUser());
        }

    }

    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }

    @Override
    public void onStart() {
        firebaseAuth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        firebaseAuth.removeAuthStateListener(authListener);
    }

    @Override
    public void attachView(HomeView view) {
        homeView = view;
    }

    @Override
    public void detachView() {
        homeView = null;
    }
}

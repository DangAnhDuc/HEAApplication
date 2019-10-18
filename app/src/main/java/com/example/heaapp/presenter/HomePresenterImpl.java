package com.example.heaapp.presenter;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.activity.HomeView;
import com.example.heaapp.view.activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePresenterImpl implements HomePresenter {
    FirebaseAuth firebaseAuth;
    HomeView homeView;
    FirebaseAuth.AuthStateListener authListener;
    Activity context;


    public HomePresenterImpl(FirebaseAuth firebaseAuth, final Activity context) {
        this.firebaseAuth = firebaseAuth;
        this.context = context;

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    ultis.setIntent(context, LoginActivity.class);
                    context.finish();
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

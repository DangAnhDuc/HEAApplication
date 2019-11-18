package com.example.heaapp.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.CurrentUserInfo;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.activity.HomeView;
import com.example.heaapp.view.activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HomePresenterImpl implements HomePresenter {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private HomeView homeView;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference databaseReference;
    private RealmService realmService;

    public HomePresenterImpl(RealmService realmService, final Context context) {
        this.realmService = realmService;
        //check user signout
        authListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                ultis.setIntent(context, LoginActivity.class);
            }
        };
    }

    @Override
    public void getCurrentUser() {
        if (firebaseAuth.getCurrentUser() != null) {
            homeView.setUser(firebaseAuth.getCurrentUser());

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("remote").child("userBodyInfo");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    CurrentUserInfo currentUserInfo = dataSnapshot.getValue(CurrentUserInfo.class);
                    if (currentUserInfo != null) {
                        realmService.modifyUserInfoAsync(currentUserInfo.getAge(), currentUserInfo.getSex(), currentUserInfo.getWeight(), currentUserInfo.getHeight(), currentUserInfo.getWaist(), currentUserInfo.getHip(), currentUserInfo.getChest(), new OnTransactionCallback() {
                            @Override
                            public void onTransactionSuccess() {

                            }

                            @Override
                            public void onTransactionError(Exception e) {

                            }
                        });

                    } else {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("remote");
                        HashMap<String, CurrentUserInfo> userInfoHashMap = new HashMap<>();
                        userInfoHashMap.put("userBodyInfo", new CurrentUserInfo(0, 0, 0, 0, "Male", 0, 0, 0));
                        databaseReference.setValue(userInfoHashMap);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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

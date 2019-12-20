package com.example.heaapp.presenter;

import androidx.annotation.NonNull;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.CurrentUserDetail;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.view.activity.HomeView;
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
    private DatabaseReference userInforDatabaseReference;
    private RealmService realmService;

    public HomePresenterImpl(RealmService realmService) {
        this.realmService = realmService;
        //check user signout
        authListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                homeView.setIntentToLogin();
            }
        };
    }

    @Override
    public void getCurrentUser() {
        if (firebaseAuth.getCurrentUser() != null) {
            homeView.setUser();

            userInforDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("remote").child("userBodyInfo");
            userInforDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    CurrentUserDetail currentUserDetail = dataSnapshot.getValue(CurrentUserDetail.class);
                    if (currentUserDetail != null) {
                        realmService.modifyUserInfoAsync(currentUserDetail.getAge(), currentUserDetail.getSex(), currentUserDetail.getWeight(), currentUserDetail.getHeight(), currentUserDetail.getWaist(), currentUserDetail.getHip(), currentUserDetail.getChest(), new OnTransactionCallback() {
                            @Override
                            public void onTransactionSuccess() {

                            }

                            @Override
                            public void onTransactionError(Exception e) {

                            }
                        });

                    } else {
                        userInforDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("remote");
                        HashMap<String, CurrentUserDetail> userInfoHashMap = new HashMap<>();
                        userInfoHashMap.put("userBodyInfo", new CurrentUserDetail(0, 0, 0, 0, "Male", 0, 0, 0));
                        userInforDatabaseReference.setValue(userInfoHashMap);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid());
            userDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    realmService.updateUser(dataSnapshot.child("username").getValue(String.class), dataSnapshot.child("imageURL").getValue(String.class), new OnTransactionCallback() {
                        @Override
                        public void onTransactionSuccess() {

                        }

                        @Override
                        public void onTransactionError(Exception e) {

                        }
                    });

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

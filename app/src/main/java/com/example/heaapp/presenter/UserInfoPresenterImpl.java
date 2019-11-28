package com.example.heaapp.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.CurrentUserInfo;
import com.example.heaapp.model.user_information.User;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.activity.UserInfoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import io.realm.RealmResults;

public class UserInfoPresenterImpl implements UserInfoPresenter {

    private UserInfoView userInfoView;
    private Context context;
    private final RealmService realmService;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    public UserInfoPresenterImpl(UserInfoView userInfoView, Context context, RealmService realmService) {
        this.userInfoView = userInfoView;
        this.context = context;
        this.realmService = realmService;
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseStorage.getInstance().getReference("upload").child(firebaseAuth.getCurrentUser().getUid());
    }

    @Override
    public void loadInfo() {
        RealmResults<CurrentUserInfo> realmResults = realmService.getCurrentUser();
        RealmResults<User> userRealmResults=realmService.getUser();
        userInfoView.displayInfo(userRealmResults.get(0).getUsername(),userRealmResults.get(0).getImageURl(),realmResults.get(0).getAge(), realmResults.get(0).getSex(),
                realmResults.get(0).getWeight(), realmResults.get(0).getHeight(), realmResults.get(0).getWaist(),
                realmResults.get(0).getHip(), realmResults.get(0).getChest());
    }

    @Override
    public void saveInfo(String age, String sex, String weight, String height, String waist, String hip, String chest) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (TextUtils.isEmpty(age) || TextUtils.isEmpty(sex) || TextUtils.isEmpty(weight) || TextUtils.isEmpty(height) || TextUtils.isEmpty(waist)
                || TextUtils.isEmpty(hip) || TextUtils.isEmpty(chest)) {
            userInfoView.displayErrorMessage("All field must be enter");
        } else {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).child("remote").child("userBodyInfo");
            databaseReference.child("age").setValue(Integer.parseInt(age));
            databaseReference.child("sex").setValue(sex);
            databaseReference.child("weight").setValue(Long.parseLong(weight));
            databaseReference.child("height").setValue(Long.parseLong(height));
            databaseReference.child("waist").setValue(Long.parseLong(waist));
            databaseReference.child("hip").setValue(Long.parseLong(hip));
            databaseReference.child("chest").setValue(Long.parseLong(chest));
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    realmService.modifyUserInfoAsync(Integer.parseInt(age), sex, Long.parseLong(weight), Long.parseLong(height), Long.parseLong(waist), Long.parseLong(hip), Long.parseLong(chest), new OnTransactionCallback() {
                        @Override
                        public void onTransactionSuccess() {
                            saveUserInfoStatusPref();
                            calculateBodyIndices();
                            userInfoView.onSaveInfoSuccess();
                        }

                        @Override
                        public void onTransactionError(Exception e) {
                            userInfoView.onSaveInfoFail(e.getMessage());
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    private void calculateBodyIndices() {
        RealmResults<CurrentUserInfo> realmResults = realmService.getCurrentUser();
        CurrentUserInfo currentUserInfo = realmResults.get(0);
        assert currentUserInfo != null;
        double BMI = currentUserInfo.getWeight() / ((currentUserInfo.getHeight() * 0.01) * (currentUserInfo.getHeight() * 0.01));
        double bodyMass;
        if (currentUserInfo.getSex().equals("Male")) {
            bodyMass = (0.32810 * currentUserInfo.getWeight()) + (0.33929 * currentUserInfo.getHeight()) - 29.5336;
        } else {
            bodyMass = (0.29569 * currentUserInfo.getWeight()) + (0.41813 * currentUserInfo.getHeight()) - 43.2933;
        }
        double bodyWater;
        if (currentUserInfo.getSex().equals("Male")) {
            bodyWater = 2.447 - 0.09156 * currentUserInfo.getAge() + 0.1074 * currentUserInfo.getHeight() + 0.3362 * currentUserInfo.getWeight();
        } else {
            bodyWater = -2.097 + 1069 * currentUserInfo.getHeight() + 0.2466 * currentUserInfo.getWeight();

        }
        double waterRequired;
        if (currentUserInfo.getAge() < 30) {
            waterRequired = (currentUserInfo.getWeight() * 40) / 28.3 * 0.0295735;
        } else if (currentUserInfo.getAge() > 30 && currentUserInfo.getAge() < 50) {
            waterRequired = (currentUserInfo.getWeight() * 35) / 28.3 * 0.0295735;
        } else {
            waterRequired = (currentUserInfo.getWeight() * 30) / 28.3 * 0.0295735;
        }

        double bloodVolume;
        if (currentUserInfo.getSex().equals("Male")) {
            bloodVolume = ((0.006012 * currentUserInfo.getHeight() * currentUserInfo.getHeight() * currentUserInfo.getHeight() * 0.39 * 0.39 * 0.39) + (14.6 * currentUserInfo.getWeight() * 2.2) + 604) * 0.001;
        } else {
            bloodVolume = ((0.005835 * currentUserInfo.getHeight() * currentUserInfo.getHeight() * currentUserInfo.getHeight() * 0.39 * 0.39 * 0.39) + (15 * currentUserInfo.getWeight() * 2.2) + 183) * 0.001;

        }

        double bodyFat;
        if (currentUserInfo.getSex().equals("Male")) {
            bodyFat = (1.2 * BMI) + (0.23 * currentUserInfo.getAge()) - (10.8 * 1) - 5.4;
        } else {
            bodyFat = (1.2 * BMI) + (0.23 * currentUserInfo.getAge()) - (10.8 * 0) - 5.4;
        }

        double FFMI;
        FFMI = (currentUserInfo.getWeight() * (1 - (bodyFat / 100))) / ((currentUserInfo.getHeight() * 0.01) * (currentUserInfo.getHeight() * 0.01));

        double dailyCal;
        if (currentUserInfo.getSex().equals("Male")) {
            dailyCal = 66.4730 + (17.7516 * currentUserInfo.getWeight()) + (5.0033 * currentUserInfo.getHeight()) - (6.7550 * currentUserInfo.getAge());
        } else {
            dailyCal = 655.0955 + (9.5634 * currentUserInfo.getWeight()) + (1.8496 * currentUserInfo.getHeight()) - (4.6756 * currentUserInfo.getAge());
        }

        realmService.addUserIndices(0, BMI, bodyMass, bodyWater, waterRequired, bloodVolume, bodyFat, FFMI, dailyCal, new OnTransactionCallback() {
            @Override
            public void onTransactionSuccess() {
                realmService.modifyNeededEnergyAsync(Double.valueOf(dailyCal).longValue(), new OnTransactionCallback() {
                    @Override
                    public void onTransactionSuccess() {

                    }

                    @Override
                    public void onTransactionError(Exception e) {

                    }
                });
            }

            @Override
            public void onTransactionError(Exception e) {

            }
        });

    }


    @Override
    public void saveUserInfoStatusPref() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isEntered", true);
        editor.apply();
    }

    @Override
    public void uploadImageFromGallery() {

        if (Common.userImageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() +
                    "." + ultis.getFileExtension(Common.userImageUri, context));

            Common.uploadUserImageTask = fileReference.putFile(Common.userImageUri);
            Common.uploadUserImageTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String imageUri = downloadUri.toString();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid());
                    databaseReference.child("imageURL").setValue(imageUri);

                    userInfoView.updateUserImage(imageUri);
                } else {
                    userInfoView.displayUploadFailed();
                }
            }).addOnFailureListener(e -> {
                userInfoView.displayUploadError(e.getMessage());
            });
        } else {
            userInfoView.displayNoImageSelected();
        }
    }


    @Override
    public void attachView(UserInfoView view) {
        userInfoView = view;
    }

    @Override
    public void detachView() {
        userInfoView = null;
    }




}

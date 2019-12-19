package com.example.heaapp.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.CurrentUserDetail;
import com.example.heaapp.model.user_information.User;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.activity.CurrentUserDetailView;
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

import static android.content.Context.MODE_PRIVATE;

public class CurrentUserDetailPresenterImpl implements CurrentUserDetailPresenter {

    private CurrentUserDetailView currentUserDetailView;
    private Context context;
    private final RealmService realmService;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    public CurrentUserDetailPresenterImpl(CurrentUserDetailView currentUserDetailView, Context context, RealmService realmService) {
        this.currentUserDetailView = currentUserDetailView;
        this.context = context;
        this.realmService = realmService;
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("upload").child(firebaseAuth.getCurrentUser().getUid());
    }

    @Override
    public void loadInfo() {
        RealmResults<CurrentUserDetail> realmResults = realmService.getCurrentUser();
        RealmResults<User> userRealmResults=realmService.getUser();
        currentUserDetailView.displayInfo(userRealmResults.get(0).getUsername(),userRealmResults.get(0).getImageURl(),realmResults.get(0).getAge(), realmResults.get(0).getSex(),
                realmResults.get(0).getWeight(), realmResults.get(0).getHeight(), realmResults.get(0).getWaist(),
                realmResults.get(0).getHip(), realmResults.get(0).getChest());
    }

    @Override
    public void saveInfo(String age, String sex, String weight, String height, String waist, String hip, String chest) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (TextUtils.isEmpty(age) || TextUtils.isEmpty(sex) || TextUtils.isEmpty(weight) || TextUtils.isEmpty(height) || TextUtils.isEmpty(waist)
                || TextUtils.isEmpty(hip) || TextUtils.isEmpty(chest)) {
            currentUserDetailView.displayErrorMessage("All field must be enter");
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
                            currentUserDetailView.onSaveInfoSuccess();
                            SharedPreferences sharedPreferences = context.getSharedPreferences("tourGuideRefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isDashboardGuideOpened", true);
                            editor.apply();
                        }

                        @Override
                        public void onTransactionError(Exception e) {
                            currentUserDetailView.onSaveInfoFail(e.getMessage());
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
        RealmResults<CurrentUserDetail> realmResults = realmService.getCurrentUser();
        CurrentUserDetail currentUserDetail = realmResults.get(0);
        assert currentUserDetail != null;
        double BMI = currentUserDetail.getWeight() / ((currentUserDetail.getHeight() * 0.01) * (currentUserDetail.getHeight() * 0.01));
        double bodyMass;
        if (currentUserDetail.getSex().equals("Male")) {
            bodyMass = (0.32810 * currentUserDetail.getWeight()) + (0.33929 * currentUserDetail.getHeight()) - 29.5336;
        } else {
            bodyMass = (0.29569 * currentUserDetail.getWeight()) + (0.41813 * currentUserDetail.getHeight()) - 43.2933;
        }
        double bodyWater;
        if (currentUserDetail.getSex().equals("Male")) {
            bodyWater = 2.447 - 0.09156 * currentUserDetail.getAge() + 0.1074 * currentUserDetail.getHeight() + 0.3362 * currentUserDetail.getWeight();
        } else {
            bodyWater = -2.097 + 1069 * currentUserDetail.getHeight() + 0.2466 * currentUserDetail.getWeight();

        }
        double waterRequired;
        if (currentUserDetail.getAge() < 30) {
            waterRequired = (currentUserDetail.getWeight() * 40) / 28.3 * 0.0295735;
        } else if (currentUserDetail.getAge() > 30 && currentUserDetail.getAge() < 50) {
            waterRequired = (currentUserDetail.getWeight() * 35) / 28.3 * 0.0295735;
        } else {
            waterRequired = (currentUserDetail.getWeight() * 30) / 28.3 * 0.0295735;
        }

        double bloodVolume;
        if (currentUserDetail.getSex().equals("Male")) {
            bloodVolume = ((0.006012 * currentUserDetail.getHeight() * currentUserDetail.getHeight() * currentUserDetail.getHeight() * 0.39 * 0.39 * 0.39) + (14.6 * currentUserDetail.getWeight() * 2.2) + 604) * 0.001;
        } else {
            bloodVolume = ((0.005835 * currentUserDetail.getHeight() * currentUserDetail.getHeight() * currentUserDetail.getHeight() * 0.39 * 0.39 * 0.39) + (15 * currentUserDetail.getWeight() * 2.2) + 183) * 0.001;

        }

        double bodyFat;
        if (currentUserDetail.getSex().equals("Male")) {
            bodyFat = (1.2 * BMI) + (0.23 * currentUserDetail.getAge()) - (10.8 * 1) - 5.4;
        } else {
            bodyFat = (1.2 * BMI) + (0.23 * currentUserDetail.getAge()) - (10.8 * 0) - 5.4;
        }

        double FFMI;
        FFMI = (currentUserDetail.getWeight() * (1 - (bodyFat / 100))) / ((currentUserDetail.getHeight() * 0.01) * (currentUserDetail.getHeight() * 0.01));

        double dailyCal;
        if (currentUserDetail.getSex().equals("Male")) {
            dailyCal = 66.4730 + (17.7516 * currentUserDetail.getWeight()) + (5.0033 * currentUserDetail.getHeight()) - (6.7550 * currentUserDetail.getAge());
        } else {
            dailyCal = 655.0955 + (9.5634 * currentUserDetail.getWeight()) + (1.8496 * currentUserDetail.getHeight()) - (4.6756 * currentUserDetail.getAge());
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

                    currentUserDetailView.updateUserImage(imageUri);
                } else {
                    currentUserDetailView.displayUploadFailed();
                }
            }).addOnFailureListener(e -> {
                currentUserDetailView.displayUploadError(e.getMessage());
            });
        } else {
            currentUserDetailView.displayNoImageSelected();
        }
    }


    @Override
    public void attachView(CurrentUserDetailView view) {
        currentUserDetailView = view;
    }

    @Override
    public void detachView() {
        currentUserDetailView = null;
    }




}

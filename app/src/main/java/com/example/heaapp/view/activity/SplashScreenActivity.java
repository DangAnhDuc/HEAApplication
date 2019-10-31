package com.example.heaapp.view.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heaapp.R;
import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.CurrentUserInfo;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.ultis.ultis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class SplashScreenActivity extends AppCompatActivity   {

    @BindView(R.id.imgSplash)
    ImageView imgSplash;
    @BindView(R.id.txtSplash)
    TextView txtSplash;
    public static AtomicLong dailySummaryPrimaryKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        Animation splashanimation= AnimationUtils.loadAnimation(this,R.anim.splashtransition);
        imgSplash.setAnimation(splashanimation);
        txtSplash.setAnimation(splashanimation);
        //init database
        Realm.init(getApplicationContext());
        Realm realm=Realm.getDefaultInstance();
        RealmService realmService= RealmService.getInstance();
        //check if the app is just install
        try {
            dailySummaryPrimaryKey=new AtomicLong(realm.where(DailySummary.class).max("id").longValue()+1);
        }
        catch (Exception e){
            realm.beginTransaction();

            //create database table for daily summary
            DailySummary dailySummary = realm.createObject(DailySummary.class);
            dailySummary.setId(0);
            dailySummary.setDate(Common.today);
            dailySummary.setWaterConsume(0);
            dailySummary.setEatenCalories(0);
            dailySummary.setBurnedCalories(0);

            //create database table for current user
            CurrentUserInfo currentUserInfo= realm.createObject(CurrentUserInfo.class);
            currentUserInfo.setId(0);
            currentUserInfo.setAge(0);
            currentUserInfo.setSex("Male");
            currentUserInfo.setWeight(0);
            currentUserInfo.setHeight(0);
            currentUserInfo.setWaist(0);
            currentUserInfo.setHip(0);
            currentUserInfo.setChest(0);

            dailySummaryPrimaryKey=new AtomicLong(realm.where(DailySummary.class).findAll().size());
            RealmResults<DailySummary> realmResults= realm.where(DailySummary.class).equalTo("id",0).findAll();
            realmResults.deleteAllFromRealm();
            realm.commitTransaction();
        }


        //Daily check
        RealmResults<DailySummary> realmResults= realm.where(DailySummary.class).equalTo("date",Common.today).findAll();
        if(realmResults.size()==0){
            realm.beginTransaction();
            DailySummary dailySummary = realm.createObject(DailySummary.class);
            dailySummary.setId(dailySummaryPrimaryKey.getAndIncrement());
            dailySummary.setDate(Common.today);
            dailySummary.setWaterConsume(0);
            dailySummary.setEatenCalories(0);
            dailySummary.setBurnedCalories(0);
            realm.commitTransaction();
        }
        Thread timer=new Thread(){
            public void run(){
                try {
                    sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    ultis.setIntent(SplashScreenActivity.this,OnboardingActivity.class);
                    finish();
                }
            }
        };
        timer.start();
    }

}

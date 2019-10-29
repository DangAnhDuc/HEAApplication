package com.example.heaapp.view.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.heaapp.R;
import com.example.heaapp.model.user_information.CurrentUserInfo;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.ultis.ultis;

import java.util.concurrent.atomic.AtomicLong;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class SplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.imgSplash)
    ImageView imgSplash;
    @BindView(R.id.txtSplash)
    TextView txtSplash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        Animation splashanimation= AnimationUtils.loadAnimation(this,R.anim.splashtransition);
        imgSplash.setAnimation(splashanimation);
        txtSplash.setAnimation(splashanimation);
        Realm.init(getApplicationContext());
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

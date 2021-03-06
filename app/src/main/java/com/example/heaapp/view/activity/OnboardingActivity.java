package com.example.heaapp.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.heaapp.R;
import com.example.heaapp.adapter.OnboardingPagerAdapter;
import com.example.heaapp.model.airweather.OnboardingItem;
import com.example.heaapp.ultis.ultis;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnboardingActivity extends AppCompatActivity {

    @BindView(R.id.screen_viewpager)
    ViewPager screenViewpager;
    @BindView(R.id.btn_get_started)
    Button btnGetStarted;
    @BindView(R.id.tv_skip)
    TextView tvSkip;

    OnboardingPagerAdapter onboardingPagerAdapter;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.tab_indicator)
    TabLayout tabIndicator;

    int position = 0;
    Animation btnAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (restorePrefsData()) {
            ultis.setIntent(OnboardingActivity.this, LoginActivity.class);
            finish();
        }
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);

        btnAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buttonanimation);

        //create onboarding list
        final List<OnboardingItem> onboardingItemList = new ArrayList<>();
        onboardingItemList.add(new OnboardingItem(getString(R.string.title_workout), getString(R.string.des_workout), R.drawable.img1));
        onboardingItemList.add(new OnboardingItem(getString(R.string.title_health_summary), getString(R.string.des_health_summary), R.drawable.img2));
        onboardingItemList.add(new OnboardingItem(getString(R.string.title_health_infomation), getString(R.string.des_health_info), R.drawable.img3));

        onboardingPagerAdapter = new OnboardingPagerAdapter(this, onboardingItemList);
        screenViewpager.setAdapter(onboardingPagerAdapter);

        tabIndicator.setupWithViewPager(screenViewpager);
        btnNext.setOnClickListener(v -> {
            position = screenViewpager.getCurrentItem();
            if (position < onboardingItemList.size()) {
                position++;
                screenViewpager.setCurrentItem(position);
            }
            if (position == onboardingItemList.size() - 1) {
                loadLastPager();
            }
        });

        tabIndicator.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == onboardingItemList.size() - 1) {
                    loadLastPager();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnGetStarted.setOnClickListener(v -> {
            ultis.setIntent(OnboardingActivity.this, LoginActivity.class);
            savePrefsData();
            finish();
        });

        tvSkip.setOnClickListener(v -> {
            ultis.setIntent(OnboardingActivity.this, LoginActivity.class);

            savePrefsData();
            finish();
        });
    }

    private boolean restorePrefsData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isOpened", false);
    }

    //check onboarding are seen
    private void savePrefsData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isOpened", true);
        editor.apply();
    }

    private void loadLastPager() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnGetStarted.setAnimation(btnAnimation);
        tvSkip.setVisibility(View.INVISIBLE);
    }
}

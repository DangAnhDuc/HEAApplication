package com.example.heaapp.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.heaapp.R;
import com.example.heaapp.presenter.HomePresenterImpl;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.fragment.DashBoardFragment;
import com.example.heaapp.view.fragment.HealthInfoFragment;
import com.example.heaapp.view.fragment.WorkoutFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeView {

    @BindView(R.id.activity_main_drawer)
    DrawerLayout sideBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tab_title)
    TextView tabTitle;
    @BindView(R.id.nav_view)
    NavigationView navView;
    private ActionBarDrawerToggle mDrawerToggle;

    HomePresenterImpl homePresenter;
    private boolean doubleBackToExitPressedOnce = false;

    private int flagLang ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        RealmService realmService = RealmService.getInstance();
        homePresenter = new HomePresenterImpl(realmService, this);
        homePresenter.attachView(this);
        homePresenter.getCurrentUser();
    }

    private void initView() {
        //setup 3 main fragment
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new WorkoutFragment(), getString(R.string.title_workout));
        viewPagerAdapter.addFragments(new DashBoardFragment(), getString(R.string.title_health_summary));
        viewPagerAdapter.addFragments(new HealthInfoFragment(), getString(R.string.title_health_infomation));


        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
        tabTitle.setText(getString(R.string.title_health_summary));

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_workout_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_doctor_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_news_icon);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        tabTitle.setText(getString(R.string.title_workout));
                        break;
                    case 2:
                        tabTitle.setText(getString(R.string.title_health_infomation));
                        break;
                    default:
                        tabTitle.setText(getString(R.string.title_health_summary));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, sideBarLayout, R.string.toggle_open, R.string.toggle_close);
        sideBarLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.side_bar_logout:
                    homePresenter.signOut();
                    ultis.showSuccessMessage(getContext(), getString(R.string.msg_signout_success));
                    break;
                case R.id.side_bar_info_user:
                    ultis.setIntent(getContext(), CurrentUserDetailActivity.class);
                    break;
                case R.id.side_bar_lang:
                    showMultipleLanguage();
                    break;
                case R.id.side_bar_remider:
                    ultis.setIntent(this, ReminderActivity.class);
                    break;

            }
            return true;
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            item.getItemId();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        homePresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        homePresenter.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.detachView();
    }

    @Override
    public void setEnabled(boolean isEnabled) {

    }

    @Override
    public void setUser(FirebaseUser user) {

    }

    @Override
    public void setIntentToLogin() {
        ultis.setIntent(getContext(), LoginActivity.class);
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        ultis.showWarningMessage(this, getString(R.string.msg_press_to_exit));
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;


        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        void addFragments(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);

        }
    }

    private void showMultipleLanguage() {
        String[] listLang = getResources().getStringArray(R.array.lang);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this,R.style.TimePicker);
        mBuilder.setTitle(getString(R.string.choose_lang));
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        assert i != null;
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mBuilder.setSingleChoiceItems(listLang, getShareFrefFlagLang(), (dialog, which) -> {
            if (which == 0) {
                flagLang = 0;
                saveShareFrefLang("en",flagLang);
                recreate();
                finish();
                startActivity(i);
            }
            if (which == 1) {
                flagLang = 1;
                saveShareFrefLang("vi",flagLang);
                recreate();
                finish();
                startActivity(i);
            }
            if (which == 2) {
                flagLang = 2;
                String sysLang = Locale.getDefault().getLanguage();
                saveShareFrefLang(sysLang,flagLang);
                recreate();
                finish();
                startActivity(i);
            }
            dialog.dismiss();

        });
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void saveShareFrefLang(String lang,int flag){
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("Lang", lang);
        editor.putInt("FlagLang",flag);
        editor.apply();
    }


    private int getShareFrefFlagLang(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        int flag = sharedPreferences.getInt("FlagLang", 2);
        return flag;
    }
}

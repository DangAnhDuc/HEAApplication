package com.example.heaapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.heaapp.R;
import com.example.heaapp.presenter.HomePresenterImpl;
import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.fragment.DashBoardFragment;
import com.example.heaapp.view.fragment.HealthInforFragment;
import com.example.heaapp.view.fragment.WorkoutFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
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
    FirebaseAuth firebaseAuth;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        initView();
        homePresenter = new HomePresenterImpl(firebaseAuth, this);
        homePresenter.attachView(this);
        homePresenter.getCurrentUser();
    }

    private void initView() {
        //setup 3 main fragment
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new WorkoutFragment(), "Workout");
        viewPagerAdapter.addFragments(new DashBoardFragment(), "Dashboard");
        viewPagerAdapter.addFragments(new HealthInforFragment(), "Health Infor");


        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
        tabTitle.setText("Health sum");

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_workout_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_doctor_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_news_icon);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        tabTitle.setText("Workout");
                        break;
                    case 1:
                        tabTitle.setText("Dashboard");
                        break;
                    case 2:
                        tabTitle.setText("Health Infor");
                        break;
                    default:
                        tabTitle.setText("Health sum");
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

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.side_bar_logout:
                        homePresenter.signOut();
                        ultis.showMessage(getContext(),"Sign out successfully!");
                }
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            if (item.getItemId() == R.id.side_bar_logout) {

            }
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
        ultis.showMessage(this, "Press once again to exit!");
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragments(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);

        }
    }
}

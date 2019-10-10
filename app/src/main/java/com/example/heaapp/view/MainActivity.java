package com.example.heaapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.heaapp.R;
import com.example.heaapp.fragment.DashBoardFragment;
import com.example.heaapp.fragment.HealthInforFragment;
import com.example.heaapp.fragment.WorkoutFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        initView();
    }

    private void initView() {
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new WorkoutFragment(),"Workout");
        viewPagerAdapter.addFragments(new DashBoardFragment(),"Dashboard");
        viewPagerAdapter.addFragments(new HealthInforFragment(),"Health Infor");


        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragments(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);

        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);

        }
    }
}

package com.example.heaapp.view.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.heaapp.R;
import com.example.heaapp.base.BaseFragment;


public class HealthInforFragment extends BaseFragment {

    @Override
    public BaseFragment provideYourFragment() {
        return new HealthInforFragment();

    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_infor,parent,false);
        return view;
    }


}

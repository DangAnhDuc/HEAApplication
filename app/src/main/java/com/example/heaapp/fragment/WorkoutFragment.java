package com.example.heaapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.heaapp.R;
import com.example.heaapp.base.BaseFragment;


public class WorkoutFragment extends BaseFragment {


    @Override
    public BaseFragment provideYourFragment() {
        return new WorkoutFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout,parent,false);
        return view;    }
}

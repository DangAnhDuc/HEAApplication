package com.example.heaapp.base;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.heaapp.R;

public abstract  class BaseFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = provideYourFragmentView(inflater,container,savedInstanceState);
        return view;
    }
    public abstract BaseFragment provideYourFragment();
    public abstract View provideYourFragmentView(LayoutInflater inflater,ViewGroup parent, Bundle savedInstanceState);
}

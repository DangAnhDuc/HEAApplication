package com.example.heaapp.view.activity;

import com.example.heaapp.base.BaseView;
import com.google.firebase.auth.FirebaseUser;

public interface HomeView extends BaseView {
    void setEnabled(boolean isEnabled);
    void setUser(FirebaseUser user);
}

package com.example.heaapp.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.heaapp.R;
import com.example.heaapp.presenter.WebviewNewsPresenterImpl;

import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewNewsActivity extends AppCompatActivity implements WebviewNewsView {
    @BindView(R.id.location_view)
    TextView locationView;
    @BindView(R.id.page_progress)
    ProgressBar pageProgress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.geckoview)
    GeckoView geckoview;

    private WebviewNewsPresenterImpl webviewNewsPresenter;
    private String newUrl;
    private String newDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_news);
        ButterKnife.bind(this);

        pageProgress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#0e9577"), PorterDuff.Mode.MULTIPLY);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        newUrl = extras.getString("URL");
        newDomain = extras.getString("Domain");

        webviewNewsPresenter = new WebviewNewsPresenterImpl(this, getContext());
        webviewNewsPresenter.initWebview(newUrl);
    }


    @Override
    public void setupSession(GeckoSession geckoSession) {
        geckoview.setSession(geckoSession);
        locationView.setText(newDomain);
    }

    @Override
    public void onProgressLoading() {
        pageProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressLoaded() {
        pageProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayProgress(int progress) {
        pageProgress.setProgress(progress);

    }

    @Override
    public Context getContext() {
        return this;
    }
}


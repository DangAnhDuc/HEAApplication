package com.example.heaapp.view.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.heaapp.R;

import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewNewsActivity extends AppCompatActivity implements GeckoSession.ProgressDelegate {

    String url;
    String domain;
    @BindView(R.id.location_view)
    TextView locationView;
    @BindView(R.id.page_progress)
    ProgressBar pageProgress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.geckoview)
    GeckoView geckoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_news);
        ButterKnife.bind(this);
        pageProgress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#0e9577"), PorterDuff.Mode.MULTIPLY);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //create gecko session
        Bundle extras = getIntent().getExtras();
        url = extras.getString("URL");
        domain = extras.getString("Domain");
        GeckoSession geckoSession = new GeckoSession();
        geckoSession.open(GeckoRuntime.getDefault(this));
        geckoview.setSession(geckoSession);
        locationView.setText(domain);
        geckoSession.loadUri(url);
        geckoSession.setProgressDelegate(this);
    }

    @Override
    public void onProgressChange(@NonNull GeckoSession session, int progress) {
        pageProgress.setProgress(progress);
        if (1 <= progress && progress <= 85) {
            pageProgress.setVisibility(View.VISIBLE);
        } else {
            pageProgress.setVisibility(View.INVISIBLE);
        }
    }
}


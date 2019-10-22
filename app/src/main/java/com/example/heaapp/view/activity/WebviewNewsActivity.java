package com.example.heaapp.view.activity;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.heaapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewNewsActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_news);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString("URL");
            webView.loadUrl(url);
        }
    }


}

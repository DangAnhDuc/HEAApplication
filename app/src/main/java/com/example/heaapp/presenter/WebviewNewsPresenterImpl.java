package com.example.heaapp.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.heaapp.view.activity.WebviewNewsView;

import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;

public class WebviewNewsPresenterImpl implements WebviewNewsPresenter, GeckoSession.ProgressDelegate {
    private WebviewNewsView webviewNewsView;
    private Context context;

    public WebviewNewsPresenterImpl(WebviewNewsView webviewNewsView, Context context) {
        this.webviewNewsView = webviewNewsView;
        this.context = context;
    }

    @Override
    public void initWebview(String url) {
        //create gecko session

        GeckoSession geckoSession = new GeckoSession();
        geckoSession.open(GeckoRuntime.getDefault(context));
        webviewNewsView.setupSession(geckoSession);
        geckoSession.loadUri(url);
        geckoSession.setProgressDelegate(this);
    }

    @Override
    public void onProgressChange(@NonNull GeckoSession session, int progress) {
        webviewNewsView.displayProgress(progress);
        if (1 <= progress && progress <= 85) {
            webviewNewsView.onProgressLoading();
        } else {
            webviewNewsView.onProgressLoaded();
        }
    }

    @Override
    public void attachView(WebviewNewsView view) {
        webviewNewsView = view;
    }

    @Override
    public void detachView() {
        webviewNewsView = null;
    }
}

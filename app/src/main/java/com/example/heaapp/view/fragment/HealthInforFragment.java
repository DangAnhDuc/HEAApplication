package com.example.heaapp.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.heaapp.R;
import com.example.heaapp.adapter.NewsAdapter;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.airweather.CityInfor;
import com.example.heaapp.model.news.Article;
import com.example.heaapp.presenter.HealthInfoPresenterImpl;
import com.example.heaapp.ultis.ultis;
import com.example.heaapp.view.activity.WebviewNewsActivity;

import java.util.List;


public class HealthInforFragment extends BaseFragment implements HealthInforView, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView newsRecyclerview;
    private HealthInfoPresenterImpl healthInforPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private TextView tv_temperature, tv_pressure, tv_humidity, tv_windspeed, tv_winddirection, tv_airquality, tv_location, tv_timestamp;
    private ImageView img_weather_status;
    private ProgressDialog progressDialog;

    @Override
    public BaseFragment provideYourFragment() {
        return new HealthInforFragment();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_infor, parent, false);

        tv_temperature = view.findViewById(R.id.desc_temp);
        tv_pressure = view.findViewById(R.id.desc_pressure);
        tv_humidity = view.findViewById(R.id.desc_humidity);
        tv_windspeed = view.findViewById(R.id.desc_windspeed);
        tv_winddirection = view.findViewById(R.id.desc_winddirect);
        tv_airquality = view.findViewById(R.id.desc_airquality);
        img_weather_status = view.findViewById(R.id.weather_status);
        tv_location = view.findViewById(R.id.tv_location);
        tv_timestamp = view.findViewById(R.id.tv_timestamp);
        healthInforPresenter = new HealthInfoPresenterImpl(this);
        newsRecyclerview = view.findViewById(R.id.new_recylcerview);
        newsRecyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        newsRecyclerview.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.show();
        healthInforPresenter.getLatestData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        healthInforPresenter.disposeApi();
    }

    @Override
    public void getListNewsSuccess(List<Article> articles) {
        progressDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), articles);
        newsRecyclerview.setAdapter(newsAdapter);
        newsAdapter.setOnItemListener(article -> {
            Intent intent = new Intent(getContext(), WebviewNewsActivity.class);
            intent.putExtra("URL", article.getUrl());
            intent.putExtra("Domain", article.getSource().getName());
            startActivity(intent);
        });
    }

    @Override
    public void getListNewsFailed(String message) {
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void getCityInfoSuccess(CityInfor cityInfor) {
        progressDialog.dismiss();
        tv_temperature.setText(String.format("%d °C", cityInfor.getData().getCurrent().getWeather().getTp()));
        tv_pressure.setText(String.format("%d hPa", cityInfor.getData().getCurrent().getWeather().getPr()));
        tv_humidity.setText(String.format("%d %%", cityInfor.getData().getCurrent().getWeather().getHu()));
        tv_windspeed.setText(String.format("%s m/s", cityInfor.getData().getCurrent().getWeather().getWs()));
        tv_winddirection.setText(String.format("%d°", cityInfor.getData().getCurrent().getWeather().getWd()));
        tv_airquality.setText(String.format("%d AQI", cityInfor.getData().getCurrent().getPollution().getAqius()));

        int id = getContext().getResources().getIdentifier("ic" + cityInfor.getData().getCurrent().getWeather().getIc(), "drawable", getContext().getPackageName());
        img_weather_status.setImageResource(id);
        tv_location.setText(String.format("%s,%s", cityInfor.getData().getCity(), cityInfor.getData().getCountry()));
        tv_timestamp.setText(cityInfor.getData().getCurrent().getWeather().getTs().substring(0, 10));


    }

    @Override
    public void getCityInfoFailed(String message) {
        ultis.showMessage(getContext(), message);
    }


    @Override
    public void onRefresh() {
        healthInforPresenter.getLatestData();
    }
}

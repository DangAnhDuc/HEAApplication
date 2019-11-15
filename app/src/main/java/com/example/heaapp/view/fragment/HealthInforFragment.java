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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HealthInforFragment extends BaseFragment implements HealthInforView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.weather_status)
    ImageView weatherStatus;
    @BindView(R.id.desc_temp)
    TextView descTemp;
    @BindView(R.id.desc_pressure)
    TextView descPressure;
    @BindView(R.id.desc_humidity)
    TextView descHumidity;
    @BindView(R.id.desc_windspeed)
    TextView descWindspeed;
    @BindView(R.id.desc_winddirect)
    TextView descWinddirect;
    @BindView(R.id.desc_airquality)
    TextView descAirquality;
    @BindView(R.id.new_recylcerview)
    RecyclerView newRecylcerview;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_timestamp)
    TextView tvTimestamp;
    private HealthInfoPresenterImpl healthInforPresenter;
    private ProgressDialog progressDialog;
    private Unbinder unbinder;

    @Override
    public BaseFragment provideYourFragment() {
        return new HealthInforFragment();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_infor, parent, false);

        unbinder = ButterKnife.bind(this, view);
        healthInforPresenter = new HealthInfoPresenterImpl(this);
        newRecylcerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        newRecylcerview.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!getUserVisibleHint()) {
            return;
        }
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.show();
        healthInforPresenter.getLatestData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        healthInforPresenter.disposeApi();
        unbinder.unbind();
    }

    @Override
    public void getListNewsSuccess(List<Article> articles) {
        progressDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), articles);
        newRecylcerview.setAdapter(newsAdapter);
        newsAdapter.setOnItemListener(article -> {
            Intent intent = new Intent(getContext(), WebviewNewsActivity.class);
            intent.putExtra("URL", article.getUrl());
            intent.putExtra("Domain", article.getSource().getName());
            startActivity(intent);
        });
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void getCityInfoSuccess(CityInfor cityInfor) {
        progressDialog.dismiss();
        descTemp.setText(String.format("%d °C", cityInfor.getData().getCurrent().getWeather().getTp()));
        descPressure.setText(String.format("%d hPa", cityInfor.getData().getCurrent().getWeather().getPr()));
        descHumidity.setText(String.format("%d %%", cityInfor.getData().getCurrent().getWeather().getHu()));
        descWindspeed.setText(String.format("%s m/s", cityInfor.getData().getCurrent().getWeather().getWs()));
        descWinddirect.setText(String.format("%d°", cityInfor.getData().getCurrent().getWeather().getWd()));
        descAirquality.setText(String.format("%d AQI", cityInfor.getData().getCurrent().getPollution().getAqius()));

        int id = Objects.requireNonNull(getContext()).getResources().getIdentifier("ic" + cityInfor.getData().getCurrent().getWeather().getIc(), "drawable", getContext().getPackageName());
        weatherStatus.setImageResource(id);
        tvLocation.setText(String.format("%s,%s", cityInfor.getData().getCity(), cityInfor.getData().getCountry()));
        tvTimestamp.setText(cityInfor.getData().getCurrent().getWeather().getTs().substring(0, 10));


    }

    @Override
    public void getLatestDataFailed(String message) {
        ultis.showMessage(getContext(), message);
    }


    @Override
    public void onRefresh() {
        healthInforPresenter.getLatestData();
    }
}

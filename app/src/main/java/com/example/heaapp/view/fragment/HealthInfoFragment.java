package com.example.heaapp.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
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

import static android.content.Context.MODE_PRIVATE;


public class HealthInfoFragment extends BaseFragment implements HealthInforView, SwipeRefreshLayout.OnRefreshListener {
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
    ShimmerRecyclerView newRecylcerview;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_timestamp)
    TextView tvTimestamp;
    @BindView(R.id.title_windspeed)
    TextView titleWindspeed;
    @BindView(R.id.layout_weather)
    LinearLayout layoutWeather;
    @BindView(R.id.layout_animation_view)
    LinearLayout layoutAnimationView;
    private HealthInfoPresenterImpl healthInfoPresenter;
    private Unbinder unbinder;

    @Override
    public BaseFragment provideYourFragment() {
        return new HealthInfoFragment();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_infor, parent, false);

        unbinder = ButterKnife.bind(this, view);
        healthInfoPresenter = new HealthInfoPresenterImpl(this, getContext());
        newRecylcerview.showShimmerAdapter();
        try {
            displayWeatherInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        swipeRefreshLayout.setRefreshing(true);
        healthInfoPresenter.getNewsData();
        healthInfoPresenter.permissionRequest();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        healthInfoPresenter.disposeApi();
        unbinder.unbind();
    }

    @Override
    public void getListNewsSuccess(List<Article> articles) {
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
        swipeRefreshLayout.setRefreshing(false);
        displayWeatherField(true);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("weatherPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("temperature", cityInfor.getData().getCurrent().getWeather().getTp().toString());
        editor.putString("pressure", cityInfor.getData().getCurrent().getWeather().getPr().toString());
        editor.putString("humidity", cityInfor.getData().getCurrent().getWeather().getHu().toString());
        editor.putString("windspeed", cityInfor.getData().getCurrent().getWeather().getWs().toString());
        editor.putString("winddirection", cityInfor.getData().getCurrent().getWeather().getWd().toString());
        editor.putString("airquality", cityInfor.getData().getCurrent().getPollution().getAqius().toString());
        editor.putString("icon", cityInfor.getData().getCurrent().getWeather().getIc());
        editor.putString("city", cityInfor.getData().getCity());
        editor.putString("country", cityInfor.getData().getCountry());
        editor.putString("timestamp", cityInfor.getData().getCurrent().getWeather().getTs().substring(0, 10));
        editor.apply();
        displayWeatherInfo();
    }

    private void displayWeatherInfo() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("weatherPrefs", MODE_PRIVATE);
        descTemp.setText(String.format("%s °C", sharedPreferences.getString("temperature", "N/a")));
        descPressure.setText(String.format("%s hPa", sharedPreferences.getString("pressure", "N/a")));
        descHumidity.setText(String.format("%s %%", sharedPreferences.getString("humidity", "N/a")));
        descWindspeed.setText(String.format("%s m/s", sharedPreferences.getString("windspeed", "N/a")));
        descWinddirect.setText(String.format("%s°", sharedPreferences.getString("winddirection", "N/a")));
        descAirquality.setText(String.format("%s AQI", sharedPreferences.getString("airquality", "N/a")));
        int id = Objects.requireNonNull(getContext()).getResources().getIdentifier("ic" + sharedPreferences.getString("icon", "Na"), "drawable", getContext().getPackageName());
        weatherStatus.setImageResource(id);
        tvLocation.setText(String.format("%s,%s", sharedPreferences.getString("city", "N/a"), sharedPreferences.getString("country", "N/a")));
        tvTimestamp.setText(sharedPreferences.getString("timestamp", "N/a"));
    }

    @Override
    public void permissionDenied() {
        swipeRefreshLayout.setRefreshing(false);
        displayWeatherField(false);
        ultis.showWarningMessage(getContext(), "Please allow location permission!");
    }

    @Override
    public void locationDisable() {
        displayWeatherField(false);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void displayWeatherField(Boolean isEnable) {
        if (!isEnable) {
            layoutWeather.setVisibility(View.GONE);
            layoutAnimationView.setVisibility(View.VISIBLE);
        } else {
            layoutWeather.setVisibility(View.VISIBLE);
            layoutAnimationView.setVisibility(View.GONE);
        }

    }

    @Override
    public void getLatestDataFailed(String message) {
        ultis.showErrorMessage(getContext(), message);
    }


    @Override
    public void onRefresh() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionDenied();
        } else {
            healthInfoPresenter.setLocation();
            healthInfoPresenter.getNewsData();

        }
    }
}

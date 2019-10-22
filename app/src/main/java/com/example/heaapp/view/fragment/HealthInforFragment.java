package com.example.heaapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.heaapp.R;
import com.example.heaapp.api.AirApiServices;
import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.CityInfor;
import com.example.heaapp.ultis.ultis;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class HealthInforFragment extends BaseFragment {
    TextView txt_ts,txtAQIUS,txtAQICN,txtMainus,txtMaincn;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    @Override
    public BaseFragment provideYourFragment() {
        return new HealthInforFragment();


    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_infor, parent, false);
         txt_ts=(TextView) view.findViewById(R.id.txt_ts);
         txtAQIUS=(TextView) view.findViewById(R.id.txt_AQIUS);
         txtAQICN=(TextView) view.findViewById(R.id.txt_AQICN);
         txtMainus=(TextView) view.findViewById(R.id.txt_mainus);
         txtMaincn=(TextView) view.findViewById(R.id.txt_maincn);

        loadJSON();
        return view;
    }

    private void loadJSON() {
        AirApiServices airApiServices= ApiUtils.getAirApiService();
        Disposable disposable= airApiServices.getHCMObs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError, this::handleSuccess);
        compositeDisposable.add(disposable);
    }

    private void handleSuccess() {
        ultis.showMessage(getContext(),"Get data success!");
    }

    private void handleError(Throwable throwable) {
        ultis.showMessage(getContext(),"Error " + throwable.getLocalizedMessage());

    }

    private void handleResponse(CityInfor cityInfor) {
        txt_ts.setText(cityInfor.getData().getCurrent().getPollution().getTs());
        txtAQIUS.setText(String.valueOf(cityInfor.getData().getCurrent().getPollution().getAqius()));
        txtMainus.setText(cityInfor.getData().getCurrent().getPollution().getMainus());
        txtAQICN.setText(String.valueOf(cityInfor.getData().getCurrent().getPollution().getAqicn()));
        txtMaincn.setText(cityInfor.getData().getCurrent().getPollution().getMaincn());
    }

    private void displayInfor(CityInfor cityInfor) {

    }


}

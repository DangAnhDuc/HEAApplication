package com.example.heaapp.view.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heaapp.R;
import com.example.heaapp.adapter.TestAdapter;
import com.example.heaapp.api.ApiUtils;
import com.example.heaapp.api.TestApiServicers;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.Post;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class HealthInforFragment extends BaseFragment {
    private List<Post> mPostArrayList;
    private TestAdapter mAdapter;

    RecyclerView mRecyclerView;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    @Override
    public BaseFragment provideYourFragment() {
        return new HealthInforFragment();

    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_infor,parent,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        loadJSON();
        return view;
    }

    private void loadJSON() {
        TestApiServicers requestInterface = ApiUtils.getTestApiServices();
        Disposable disposable = requestInterface.getPost()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError, this::handleSuccess);

        compositeDisposable.add(disposable);
    }

    private void handleResponse(List<Post> posts) {

        mPostArrayList = posts;
        mAdapter = new TestAdapter(getContext(),mPostArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void handleError(Throwable error) {

        Toast.makeText(getContext(), "Error " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handleSuccess() {
        Toast.makeText(getContext(), "Get data success! ", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}

package com.example.heaapp.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.heaapp.R;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.workout.ExerciseImage.Result;
import com.example.heaapp.presenter.ExerciseImagePresenter;
import com.example.heaapp.presenter.ExerciseImagePresenterImpl;
import com.example.heaapp.view.activity.ExerciseImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ExerciseImageFragment extends BaseFragment implements ExerciseImageView {
    @BindView(R.id.exerImgFrag)
    ImageView exeImg;
    List<Result> listResult;
    int exerID;
    private Unbinder unbinder;
    @Override
    public BaseFragment provideYourFragment() {
        return null;
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_image, parent, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        assert bundle != null;
        exerID = bundle.getInt("exerID");

        ExerciseImagePresenter exePresneter = new ExerciseImagePresenterImpl(this, exerID);
        exePresneter.getListImage();

        return view;
    }

    @Override
    public void getImageSuccess(List<Result> list) {
        try {
            Handler handler = new Handler();
            Thread thread = new Thread(new Runnable() {
                int i = 0;

                @Override
                public void run() {
                    Glide.with(getContext()).load(list.get(i).getImage()).error(R.drawable.wait).into(exeImg);
                    i++;
                    if (i > list.size() - 1) {
                        i = 0;
                    }
                    handler.postDelayed(this, 2000);
                }
            });
            handler.postDelayed(thread, 2000);
        } catch (Exception ex) {
            Glide.with(this).load(R.drawable.error_404).into(exeImg);
        }
        listResult = list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

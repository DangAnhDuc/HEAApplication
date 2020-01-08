package com.example.heaapp.view.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.heaapp.R;
import com.example.heaapp.base.BaseFragment;
import com.example.heaapp.model.workout.Equipment.Result;
import com.example.heaapp.model.workout.Muscle.ListMuscle;
import com.example.heaapp.presenter.ExerciseInfoPresenter;
import com.example.heaapp.presenter.ExerciseInfoPresenterImpl;
import com.example.heaapp.ultis.ultis;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ExerciseInfoFragment extends BaseFragment implements ExerciseInfoView {


    @BindView(R.id.exercise_info_description_fragment)
    TextView txtExeInfoDes;
    @BindView(R.id.exercise_info_muscle_fragment)
    TextView txtExeInfoMus;
    @BindView(R.id.exercise_info_equipment_fragment)
    TextView txtExeInfoEquip;
    @BindView(R.id.btnExer_Info_fragment)
    Button btnExe;
//    @BindView(R.id.workout_name)
//    TextView txtNameToolBar;
    private Unbinder unbinder;
    @Override
    public BaseFragment provideYourFragment() {
        return new ExerciseInfoFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_info, parent, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        int exeId;
        assert bundle != null;
        ArrayList<Integer> CateID = bundle.getIntegerArrayList("muscles");
        ArrayList<Integer> EquipID = bundle.getIntegerArrayList("equipment");
        exeId = bundle.getInt("id");
        ExerciseInfoPresenter exerciseInfoPresenter = new ExerciseInfoPresenterImpl(CateID, EquipID, this);
        exerciseInfoPresenter.getListMuscle();
        exerciseInfoPresenter.getListEquipment();

//        txtNameToolBar.setText(Html.fromHtml("<p>" + bundle.getString("name") + "</p>"));
        txtExeInfoDes.setText(Html.fromHtml(bundle.getString("description")));
        ExerciseImageFragment exerciseImageFragment = new ExerciseImageFragment();
        btnExe.setOnClickListener(v -> {
//            Intent intent = new Intent(this, ExerciseImageActivity.class);
//            intent.putExtra("exerID", exeId);
//            startActivity(intent);

            Bundle bundle1 = new Bundle();
            bundle1.putInt("exerID",exeId);
            exerciseImageFragment.setArguments(bundle1);
            getFragmentManager().beginTransaction().replace(R.id.ExerciseFragment,exerciseImageFragment).commit();
        });
        return view;
    }

    @Override
    public void getMuscleSuccess(List<ListMuscle> nameMuscle) {
        String muscle = "";
        for (int i = 0; i <= nameMuscle.size(); i++) {
            muscle += nameMuscle.get(i).getName();
            if (i < nameMuscle.size() - 1) {
                muscle += ",";
            }
            txtExeInfoMus.setText(muscle);
        }
    }

    @Override
    public void getEquipSuccess(List<Result> nameEquip) {
        String equip = "";
        for (int i = 0; i <= nameEquip.size(); i++) {
            equip += nameEquip.get(i).getName();
            if (i < nameEquip.size() - 1) {
                equip += ",";
            }
            txtExeInfoEquip.setText(equip);
        }
    }

    @Override
    public void handleErrorListMusscle(String mes) {
        ultis.showErrorMessage(getContext(), mes);
    }

    @Override
    public void handleErrorEquip(String mes) {
        ultis.showErrorMessage(getContext(), mes);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

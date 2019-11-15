package com.example.heaapp.presenter;

import android.content.Context;

import com.example.heaapp.R;
import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.CurrentUserInfo;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.view.activity.ActivitiesAddingView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ActivitiesAddingPresenterImpl implements ActivitiesAddingPresenter {
    private ActivitiesAddingView activitiesAddingView;
    private RealmService realmService;
    private Realm realm = Realm.getDefaultInstance();
    private CurrentUserInfo currentUserInfo;
    private List<String> activitiesListMns= new ArrayList<>();
    private List<Double> METs=new ArrayList<Double>();
    private Context context;
    private List<String> activitiesName=new ArrayList<String>();

    public ActivitiesAddingPresenterImpl(ActivitiesAddingView activitiesAddingView, RealmService realmService,Context context) {
        this.activitiesAddingView = activitiesAddingView;
        this.realmService = realmService;
        this.context=context;
    }

    @Override
    public void attachView(ActivitiesAddingView view) {
        activitiesAddingView = view;
    }

    @Override
    public void detachView() {
        activitiesAddingView = null;
    }


    public void addActivities() {
        double totalEnergyBurned = realmService.getCurrentDate().get(0).getBurnedCalories();
        double totalneededCalories = realmService.getCurrentDate().get(0).getNeededCalories();
        RealmResults<CurrentUserInfo> realmResults = realmService.getCurrentUser();
        currentUserInfo = realmResults.get(0);
        for (int i=0;i<activitiesListMns.size();i++){
            try {
                double burnedEnergy= calculateBurnedEnergy(METs.get(i),activitiesListMns.get(i));
                totalneededCalories= totalneededCalories+burnedEnergy;
                totalEnergyBurned=totalEnergyBurned+burnedEnergy;
                int finalI = i;
                realmService.modifyBurnedEnergyAsync((Double.valueOf(totalEnergyBurned)).longValue(), (Double.valueOf(totalneededCalories)).longValue(), new OnTransactionCallback() {
                    @Override
                    public void onTransactionSuccess() {
                        realmService.addActivities(context.getResources().getStringArray(R.array.activitiesName)[finalI],activitiesListMns.get(finalI),Double.valueOf(burnedEnergy).longValue());
                        activitiesAddingView.addActivitiesSuccess();
                    }

                    @Override
                    public void onTransactionError(Exception e) {

                    }
                });
            }
            catch (Exception e){
            }
        }
    }

    @Override
    public void addActivitiesListMns(String sleepingMns, String deskWorkMns, String calisthenicsLightMns, String calisthenicsVigorousMns,
                                      String gymnasticMns, String walkingSlowMns, String walkingNormalMns, String walkingFastMns, String runningSlowMns
            , String runningNormalMns, String runningFastMns, String cyclingSlowMns, String cyclingNormalMns,
                                      String cyclingFastMns, String ropeJumpingMns, String swimmingMns, String yogaMns) {
        String[] arrayActivitiesMns={sleepingMns,  deskWorkMns,  calisthenicsLightMns,  calisthenicsVigorousMns,
                 gymnasticMns,  walkingSlowMns,  walkingNormalMns,  walkingFastMns,  runningSlowMns
                ,  runningNormalMns,  runningFastMns,  cyclingSlowMns,  cyclingNormalMns,
                 cyclingFastMns,  ropeJumpingMns,  swimmingMns,  yogaMns};
        activitiesListMns= Arrays.asList(arrayActivitiesMns);

        Double[] arrayMET = {0.95,1.5,3.8,8.0,3.8,2.0,3.3,4.3,5.0,10.5,23.0,3.5,8.0,15.8,8.8,9.5,8.8};
        METs= Arrays.asList(arrayMET);
        addActivities();
    }


    private double calculateBurnedEnergy(double MET, String timePeriod) {
        double burnedEnergy = ((MET * currentUserInfo.getWeight() * 3.5) / 200) * (Integer.parseInt(timePeriod));
        return burnedEnergy;
    }


}

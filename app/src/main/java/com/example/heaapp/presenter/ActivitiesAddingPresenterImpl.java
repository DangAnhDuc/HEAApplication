package com.example.heaapp.presenter;

import com.example.heaapp.callback.OnTransactionCallback;
import com.example.heaapp.model.user_information.CurrentUserInfo;
import com.example.heaapp.model.user_information.DailySummary;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.Common;
import com.example.heaapp.view.activity.ActivitiesAddingView;

import io.realm.Realm;
import io.realm.RealmResults;

public class ActivitiesAddingPresenterImpl implements ActivitiesAddingPresenter, OnTransactionCallback {
    private ActivitiesAddingView activitiesAddingView;
    private RealmService realmService;
    private Realm realm = Realm.getDefaultInstance();
    private CurrentUserInfo currentUserInfo;

    public ActivitiesAddingPresenterImpl(ActivitiesAddingView activitiesAddingView, RealmService realmService) {
        this.activitiesAddingView = activitiesAddingView;
        this.realmService = realmService;
    }

    @Override
    public void attachView(ActivitiesAddingView view) {
        activitiesAddingView = view;
    }

    @Override
    public void detachView() {
        activitiesAddingView = null;
    }


    @Override
    public void addActivities(String sleepingMns, String deskWorkMns, String calisthenicsLightMns, String calisthenicsVigorousMns,
                              String gymnasticMns, String walkingSlowMns, String walkingNormalMns, String walkingFastMns, String runningSlowMns
            , String runningNormalMns, String runningFastMns, String cyclingSlowMns, String cyclingNormalMns,
                              String cyclingFastMns, String ropeJumpingMns, String swimmingMns, String yogaMns) {

        try {
            double totalEnergyBurned = realmService.getCurrentDate().get(0).getBurnedCalories();
            double totalneededCalories = realmService.getCurrentDate().get(0).getNeededCalories();

            RealmResults<CurrentUserInfo> realmResults = realmService.getCurrentUser();
            currentUserInfo = realmResults.get(0);
            double sleepingBurnedEnergy;
            sleepingBurnedEnergy = calculateBurnedEnergy(0.95, sleepingMns);

            double deskworkBurnedEnergy;
            deskworkBurnedEnergy = calculateBurnedEnergy(1.5, deskWorkMns);

            double calisthenicLightBurnedEnergy;
            calisthenicLightBurnedEnergy = calculateBurnedEnergy(3.8, calisthenicsLightMns);

            double calisthenicVigoroustBurnedEnergy;
            calisthenicVigoroustBurnedEnergy = calculateBurnedEnergy(8, calisthenicsVigorousMns);

            double gymnasticstBurnedEnergy;
            gymnasticstBurnedEnergy = calculateBurnedEnergy(3.8, gymnasticMns);

            double walkingSlowBurnedEnergy;
            walkingSlowBurnedEnergy = calculateBurnedEnergy(2, walkingSlowMns);

            double walkingNormalBurnedEnergy;
            walkingNormalBurnedEnergy = calculateBurnedEnergy(3.3, walkingNormalMns);

            double walkingFastBurnedEnergy;
            walkingFastBurnedEnergy = calculateBurnedEnergy(4.3, walkingFastMns);

            double runningSlowBurnedEnergy;
            runningSlowBurnedEnergy = calculateBurnedEnergy(5, runningSlowMns);

            double runningNormalBurnedEnergy;
            runningNormalBurnedEnergy = calculateBurnedEnergy(10.5, runningNormalMns);

            double runningFastBurnedEnergy;
            runningFastBurnedEnergy = calculateBurnedEnergy(23, runningFastMns);

            double cyclingSlowBurnedEnergy;
            cyclingSlowBurnedEnergy = calculateBurnedEnergy(3.5, cyclingSlowMns);

            double cyclingNormalBurnedEnergy;
            cyclingNormalBurnedEnergy = calculateBurnedEnergy(8, cyclingNormalMns);

            double cyclingFastBurnedEnergy;
            cyclingFastBurnedEnergy = calculateBurnedEnergy(15.8, cyclingFastMns);

            double jumpingRopeBurnedEnergy;
            jumpingRopeBurnedEnergy = calculateBurnedEnergy(8.8, ropeJumpingMns);

            double swimmingBurnedEnergy;
            swimmingBurnedEnergy = calculateBurnedEnergy(9.5, swimmingMns);

            double yogaBurnedEnergy;
            yogaBurnedEnergy = calculateBurnedEnergy(8.8, yogaMns);

            totalneededCalories=totalneededCalories+sleepingBurnedEnergy + deskworkBurnedEnergy + calisthenicLightBurnedEnergy + calisthenicVigoroustBurnedEnergy
                    + gymnasticstBurnedEnergy + walkingSlowBurnedEnergy + walkingNormalBurnedEnergy + walkingFastBurnedEnergy + runningSlowBurnedEnergy + runningNormalBurnedEnergy + runningFastBurnedEnergy
                    + cyclingSlowBurnedEnergy + cyclingNormalBurnedEnergy + cyclingFastBurnedEnergy + jumpingRopeBurnedEnergy + swimmingBurnedEnergy + yogaBurnedEnergy;
            totalEnergyBurned = totalEnergyBurned + sleepingBurnedEnergy + deskworkBurnedEnergy + calisthenicLightBurnedEnergy + calisthenicVigoroustBurnedEnergy
                    + gymnasticstBurnedEnergy + walkingSlowBurnedEnergy + walkingNormalBurnedEnergy + walkingFastBurnedEnergy + runningSlowBurnedEnergy + runningNormalBurnedEnergy + runningFastBurnedEnergy
                    + cyclingSlowBurnedEnergy + cyclingNormalBurnedEnergy + cyclingFastBurnedEnergy + jumpingRopeBurnedEnergy + swimmingBurnedEnergy + yogaBurnedEnergy;
            realmService.modifyBurnedEnergyAsync((Double.valueOf(totalEnergyBurned)).longValue(),(Double.valueOf(totalneededCalories)).longValue(), this);
        }
        catch (Exception e){
            activitiesAddingView.addActivitiesFailed();
        }
    }

    private double calculateBurnedEnergy(double MET, String timePeriod) {
        double burnedEnergy = ((MET * currentUserInfo.getWeight() * 3.5) / 200) * (Integer.parseInt(timePeriod));
        return burnedEnergy;
    }

    @Override
    public void onTransactionSuccess() {
        activitiesAddingView.addActivitiesSuccess();
    }

    @Override
    public void onTransactionError(Exception e) {

    }
}

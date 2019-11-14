package com.example.heaapp.presenter;

import com.example.heaapp.base.BasePresenter;
import com.example.heaapp.view.activity.ActivitiesAddingView;

public interface ActivitiesAddingPresenter extends BasePresenter<ActivitiesAddingView> {
    void addActivitiesListMns(String sleepingMns, String deskWorkMns, String calisthenicsLightMns,String calisthenicsVigorousMns,
                       String gymnasticMns, String walkingSlowMns, String walkingNormalMns, String walkingFastMns, String runningSlowMns
            ,String runningNormalMns,String runningFastMns, String cyclingSlowMns,String cyclingNormalMns,
                       String cyclingFastMns, String ropeJumpingMns,String swimmingMns, String yogaMns);
}

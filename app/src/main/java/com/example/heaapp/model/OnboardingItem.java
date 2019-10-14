package com.example.heaapp.model;

public class OnboardingItem {
    String title, description;
    int onboardingImg;

    public OnboardingItem() {
    }

    public OnboardingItem(String title, String description, int onboardingImg) {
        this.title = title;
        this.description = description;
        this.onboardingImg = onboardingImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOnboardingImg() {
        return onboardingImg;
    }

    public void setOnboardingImg(int onboardingImg) {
        this.onboardingImg = onboardingImg;
    }
}

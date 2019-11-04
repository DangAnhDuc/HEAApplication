package com.example.heaapp.model.user_information;

import io.realm.RealmObject;

public class CurrentUserIndices extends RealmObject {
    long id;
    double BMI;
    double bodyMass;
    double bodyWater;
    double waterRequired;
    double bloodVolume;
    double bodyFat;
    double FFMI;
    double dailyCal;

    public CurrentUserIndices() {
    }

    public CurrentUserIndices(long id, double BMI, double bodyMass, double bodyWater, double waterRequired, double bloodVolume, double bodyFat, double FFMI, double dailyCal) {
        this.id = id;
        this.BMI = BMI;
        this.bodyMass = bodyMass;
        this.bodyWater = bodyWater;
        this.waterRequired = waterRequired;
        this.bloodVolume = bloodVolume;
        this.bodyFat = bodyFat;
        this.FFMI = FFMI;
        this.dailyCal = dailyCal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public double getBodyMass() {
        return bodyMass;
    }

    public void setBodyMass(double bodyMass) {
        this.bodyMass = bodyMass;
    }

    public double getBodyWater() {
        return bodyWater;
    }

    public void setBodyWater(double bodyWater) {
        this.bodyWater = bodyWater;
    }

    public double getWaterRequired() {
        return waterRequired;
    }

    public void setWaterRequired(double waterRequired) {
        this.waterRequired = waterRequired;
    }

    public double getBloodVolume() {
        return bloodVolume;
    }

    public void setBloodVolume(double bloodVolume) {
        this.bloodVolume = bloodVolume;
    }

    public double getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(double bodyFat) {
        this.bodyFat = bodyFat;
    }

    public double getFFMI() {
        return FFMI;
    }

    public void setFFMI(double FFMI) {
        this.FFMI = FFMI;
    }

    public double getDailyCal() {
        return dailyCal;
    }

    public void setDailyCal(double dailyCal) {
        this.dailyCal = dailyCal;
    }
}



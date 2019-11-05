package com.example.heaapp.model.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nutrients {

    @SerializedName("salt")
    @Expose
    private Salt salt;
    @SerializedName("protein")
    @Expose
    private Protein protein;
    @SerializedName("fiber")
    @Expose
    private Fiber fiber;
    @SerializedName("sugars")
    @Expose
    private Sugars sugars;
    @SerializedName("carbohydrates")
    @Expose
    private Carbohydrates carbohydrates;
    @SerializedName("saturated_fat")
    @Expose
    private SaturatedFat saturatedFat;
    @SerializedName("fat")
    @Expose
    private Fat fat;
    @SerializedName("energy_kcal")
    @Expose
    private EnergyKcal energyKcal;
    @SerializedName("energy")
    @Expose
    private Energy energy;

    public Salt getSalt() {
        return salt;
    }

    public void setSalt(Salt salt) {
        this.salt = salt;
    }

    public Protein getProtein() {
        return protein;
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    public Fiber getFiber() {
        return fiber;
    }

    public void setFiber(Fiber fiber) {
        this.fiber = fiber;
    }

    public Sugars getSugars() {
        return sugars;
    }

    public void setSugars(Sugars sugars) {
        this.sugars = sugars;
    }

    public Carbohydrates getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Carbohydrates carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public SaturatedFat getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(SaturatedFat saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public Fat getFat() {
        return fat;
    }

    public void setFat(Fat fat) {
        this.fat = fat;
    }

    public EnergyKcal getEnergyKcal() {
        return energyKcal;
    }

    public void setEnergyKcal(EnergyKcal energyKcal) {
        this.energyKcal = energyKcal;
    }

    public Energy getEnergy() {
        return energy;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }

}

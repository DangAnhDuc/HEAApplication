package com.example.heaapp.model.food;

public class Dishes {
    private String name;
    private long energy;
    private long carbs;
    private long protein;
    private long fat;

    public Dishes(String name, long energy, long carbs, long protein, long fat) {
        this.name = name;
        this.energy = energy;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
    }

    public Dishes() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getEnergy() {
        return energy;
    }

    public void setEnergy(long energy) {
        this.energy = energy;
    }

    public long getCarbs() {
        return carbs;
    }

    public void setCarbs(long carbs) {
        this.carbs = carbs;
    }

    public long getProtein() {
        return protein;
    }

    public void setProtein(long protein) {
        this.protein = protein;
    }

    public long getFat() {
        return fat;
    }

    public void setFat(long fat) {
        this.fat = fat;
    }
}

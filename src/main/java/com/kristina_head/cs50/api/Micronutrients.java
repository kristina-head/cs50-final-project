package com.kristina_head.cs50.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"vitaminA", "vitaminC", "vitaminD", "calcium", "iron", "potassium", "sodium", "unit"})
public class Micronutrients {
    private static final String UNIT = "mg";
    private float vitaminA;
    private float vitaminC;
    private float vitaminD;
    private float calcium;
    private float iron;
    private float potassium;
    private float sodium;

    public Micronutrients(float vitaminA, float vitaminC, float vitaminD,
                         float calcium, float iron, float potassium, float sodium) {
        this.vitaminA = vitaminA;
        this.vitaminC = vitaminC;
        this.vitaminD = vitaminD;
        this.calcium = calcium;
        this.iron = iron;
        this.potassium = potassium;
        this.sodium = sodium;
    }

    public String getUNIT() {
        return UNIT;
    }

    public float getVitaminA() {
        return this.vitaminA;
    }

    public float getVitaminC() {
        return this.vitaminC;
    }

    public float getVitaminD() {
        return this.vitaminD;
    }

    public float getCalcium() {
        return this.calcium;
    }

    public float getIron() {
        return this.iron;
    }

    public float getPotassium() {
        return this.potassium;
    }

    public float getSodium() {
        return this.sodium;
    }
}

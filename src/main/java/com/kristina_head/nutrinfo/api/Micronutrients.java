package com.kristina_head.nutrinfo.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"vitaminA", "vitaminC", "vitaminD", "calcium", "iron", "potassium", "sodium", "unit"})
public class Micronutrients {
    private static final String UNIT = "mg";
    private Long foodId;
    private float vitaminA;
    private float vitaminC;
    private float vitaminD;
    private float calcium;
    private float iron;
    private float potassium;
    private float sodium;

    public Micronutrients(Long foodId, float vitaminA, float vitaminC, float vitaminD,
                          float calcium, float iron, float potassium, float sodium) {
        this.foodId = foodId;
        this.vitaminA = vitaminA;
        this.vitaminC = vitaminC;
        this.vitaminD = vitaminD;
        this.calcium = calcium;
        this.iron = iron;
        this.potassium = potassium;
        this.sodium = sodium;
    }

    @JsonIgnore
    public Long getFoodId() {
        return this.foodId;
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

    public String getUNIT() {
        return UNIT;
    }
}

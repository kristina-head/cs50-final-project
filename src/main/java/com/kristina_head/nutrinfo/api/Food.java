package com.kristina_head.nutrinfo.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "amount", "unit", "calories"})
public class Food {
    private static final int AMOUNT = 100;
    private long id;
    private String name;
    private String unit;
    private float calories;
    private Macronutrients macronutrients;
    private Micronutrients micronutrients;

    public Food(long id, String name, String unit, float calories) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.calories = calories;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getAmount() {
        return AMOUNT;
    }

    public String getUnit() {
        return this.unit;
    }

    public float getCalories() {
        return this.calories;
    }

    public Macronutrients getMacronutrients() {
        return this.macronutrients;
    }

    public void setMacronutrients(Macronutrients macronutrients) {
        this.macronutrients = macronutrients;
    }

    public Micronutrients getMicronutrients() {
        return this.micronutrients;
    }

    public void setMicronutrients(Micronutrients micronutrients) {
        this.micronutrients = micronutrients;
    }
}

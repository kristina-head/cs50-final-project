package com.kristina_head.cs50.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "amount", "unit", "calories"})
public class Food {
    private static final int AMOUNT = 100;
    private long id;
    private String name;
    private String unit;
    private float calories;

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
}

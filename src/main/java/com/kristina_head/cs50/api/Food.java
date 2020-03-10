package com.kristina_head.cs50.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "calories", "amount", "unit"})

public class Food {
    private static final int AMOUNT = 100;
    private long id;
    private String name;
    private int calories;
    private String unit;

    public Food(long id, String name, int calories, String unit) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.unit = unit;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getCalories() {
        return this.calories;
    }

    public int getAmount() {
        return AMOUNT;
    }

    public String getUnit() {
        return this.unit;
    }
}


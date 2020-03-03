package com.kristina_head.cs50.api;

public class Food {
    private long id;
    private String name;
    private int calories;
    private static final int amount = 100;
    private String unit;

    public Food(String name, int calories, String unit) {
        this.name = name;
        this.calories = calories;
        this.unit = unit;
    }

    public String getName() {
        return this.name;
    }

    public int getCalories() {
        return this.calories;
    }

    public static int getAmount() {
        return amount;
    }

    public String getUnit() {
        return this.unit;
    }
}


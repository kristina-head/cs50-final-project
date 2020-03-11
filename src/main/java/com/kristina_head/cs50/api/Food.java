package com.kristina_head.cs50.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "amount", "unit", "calories", "fat"})
public class Food {

    @JsonPropertyOrder({"totalFat", "saturatedFat", "polyunsaturatedFat", "monounsaturatedFat"})
    public static class Fat {
        private float saturatedFat;
        private float polyunsaturatedFat;
        private float monounsaturatedFat;

        public Fat(float saturatedFat, float polyunsaturatedFat, float monounsaturatedFat) {
            this.saturatedFat = saturatedFat;
            this.polyunsaturatedFat = polyunsaturatedFat;
            this.monounsaturatedFat = monounsaturatedFat;
        }

        public float getTotalFat() {
            return this.saturatedFat + this.polyunsaturatedFat + this.monounsaturatedFat;
        }

        public float getSaturatedFat() {
            return this.saturatedFat;
        }

        public float getPolyunsaturatedFat() {
            return this.polyunsaturatedFat;
        }

        public float getMonounsaturatedFat() {
            return this.monounsaturatedFat;
        }
    }

    private static final int AMOUNT = 100;
    private long id;
    private String name;
    private float calories;
    private String unit;
    private Fat fat;

    public Food(long id, String name, String unit, float calories, Fat fat) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.calories = calories;
        this.fat = fat;
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

    public Fat getFat() {
        return this.fat;
    }
}

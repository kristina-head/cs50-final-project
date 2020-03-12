package com.kristina_head.cs50.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "amount", "unit", "calories", "fat", "carbohydrate", "protein"})
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

    @JsonPropertyOrder({"totalCarbohydrate", "fiber", "sugar"})
    public static class Carbohydrate {
        private float fiber;
        private float sugar;

        public Carbohydrate(float fiber, float sugar) {
            this.fiber = fiber;
            this.sugar = sugar;
        }

        public float getTotalCarbohydrate() {
            return this.fiber + this.sugar;
        }

        public float getFiber() {
            return this.fiber;
        }

        public float getSugar() {
            return this.sugar;
        }
    }

    private static final int AMOUNT = 100;
    private long id;
    private String name;
    private float calories;
    private String unit;
    private Fat fat;
    private Carbohydrate carbohydrate;
    private float protein;

    public Food(long id, String name, String unit, float calories, Fat fat, Carbohydrate carbohydrate, float protein) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.calories = calories;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
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

    public Carbohydrate getCarbohydrate() {
        return this.carbohydrate;
    }

    public float getProtein() {
        return this.protein;
    }
}

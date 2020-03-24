package com.kristina_head.cs50;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kristina_head.cs50.db.SQLiteConnection;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import static com.kristina_head.cs50.ApiKey.API_KEY;

public class DatabaseSeed {
    public static void main(final String[] args) throws IOException {

        Map<String, Integer> macronutrientIds = new HashMap<>();
        macronutrientIds.put("1258", 2);
        macronutrientIds.put("1293", 3);
        macronutrientIds.put("1292", 4);
        macronutrientIds.put("1253", 5);
        macronutrientIds.put("2000", 6);
        macronutrientIds.put("1079", 7);
        macronutrientIds.put("1003", 8);

        Map<String, Integer> micronutrientIds = new HashMap<>();
        micronutrientIds.put("1106", 2);
        micronutrientIds.put("1162", 3);
        micronutrientIds.put("1114", 4);
        micronutrientIds.put("1087", 5);
        micronutrientIds.put("1089", 6);
        micronutrientIds.put("1092", 7);
        micronutrientIds.put("1093", 8);

        String foodQuery = "INSERT INTO food (id, name, unit, calories) VALUES (?, ?, 'g', ?)";
        String macronutrientsQuery =
                "INSERT INTO macronutrients (food_id, saturated_fat, polyunsaturated_fat, monounsaturated_fat, " +
                        "cholesterol, fiber, sugar, protein) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String micronutrientsQuery =
                "INSERT INTO micronutrients (food_id, vitamin_a, vitamin_c, vitamin_d, calcium, iron, potassium, sodium)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        long idValue = 1;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        final ObjectMapper mapper = new ObjectMapper();

        for (int i = 1; i < 29; i++) {

            HttpGet searchUrl = new HttpGet("https://api.nal.usda.gov/fdc/v1/search?api_key=" + API_KEY + "&generalSearchInput=raw&includeDataTypeList=SR%20Legacy&pageNumber=" + i);
            CloseableHttpResponse searchResponse = httpclient.execute(searchUrl);
            DatabaseSeed.FoodSearch foodSearch = mapper.readValue(EntityUtils.toString(searchResponse.getEntity()), DatabaseSeed.FoodSearch.class);
            searchResponse.close();

            for (FoodSearch.Foods result : foodSearch.foods) {

                HttpGet detailsUrl = new HttpGet("https://api.nal.usda.gov/fdc/v1/" + result.fdcId + "?api_key=" + API_KEY);
                CloseableHttpResponse detailsResponse = httpclient.execute(detailsUrl);
                DatabaseSeed.FoodDetails foodDetails = mapper.readValue(EntityUtils.toString(detailsResponse.getEntity()), DatabaseSeed.FoodDetails.class);
                detailsResponse.close();

                try (Connection connection = SQLiteConnection.getConnection();
                     PreparedStatement foodStatement = connection.prepareStatement(foodQuery);
                     PreparedStatement macronutrientsStatement = connection.prepareStatement(macronutrientsQuery);
                     PreparedStatement micronutrientsStatement = connection.prepareStatement(micronutrientsQuery)) {

                    foodStatement.setLong(1, idValue);
                    foodStatement.setString(2, foodDetails.description);
                    foodStatement.setNull(3, java.sql.Types.INTEGER);

                    macronutrientsStatement.setLong(1, idValue);
                    micronutrientsStatement.setLong(1, idValue);
                    for (int j = 2; j < 8; j++) {
                        macronutrientsStatement.setNull(j, java.sql.Types.REAL);
                        micronutrientsStatement.setNull(j, java.sql.Types.REAL);
                    }

                    for (FoodDetails.FoodNutrients detail : foodDetails.foodNutrients) {
                        if (detail.nutrient.get("id").equals("1008")) {
                            foodStatement.setInt(3, Math.round(detail.amount));
                        }
                        if (macronutrientIds.containsKey(detail.nutrient.get("id"))) {
                            macronutrientsStatement.setFloat(macronutrientIds.get(detail.nutrient.get("id")), detail.amount);
                        }
                        if (micronutrientIds.containsKey(detail.nutrient.get("id"))) {
                            if (detail.nutrient.get("id").equals("1106") || detail.nutrient.get("id").equals("1114")) {
                                micronutrientsStatement.setFloat(micronutrientIds.get(detail.nutrient.get("id")), detail.amount / 1000);
                            } else {
                                micronutrientsStatement.setFloat(micronutrientIds.get(detail.nutrient.get("id")), detail.amount);
                            }
                        }
                    }
                    foodStatement.executeUpdate();
                    macronutrientsStatement.executeUpdate();
                    micronutrientsStatement.executeUpdate();

                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                idValue++;
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FoodSearch {
        public Foods[] foods;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Foods {
            public int fdcId;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FoodDetails {
        public String description;
        public FoodNutrients[] foodNutrients;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class FoodNutrients {
            public Map<String, String> nutrient;
            public float amount;
        }
    }
}


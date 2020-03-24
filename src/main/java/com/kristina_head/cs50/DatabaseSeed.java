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
import java.util.HashMap;
import java.util.Map;

import static com.kristina_head.cs50.ApiKey.API_KEY;

public class DatabaseSeed {
    public static void main(final String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet searchUrl = new HttpGet("https://api.nal.usda.gov/fdc/v1/search?api_key=" + API_KEY + "&generalSearchInput=raw&includeDataTypeList=SR%20Legacy&pageNumber=1");
        CloseableHttpResponse searchResponse = httpclient.execute(searchUrl);
        final ObjectMapper mapper = new ObjectMapper();
        DatabaseSeed.FoodSearch foodSearch = mapper.readValue(EntityUtils.toString(searchResponse.getEntity()), DatabaseSeed.FoodSearch.class);

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

        long count = 1;

        for (FoodSearch.Foods result : foodSearch.foods) {

            HttpGet detailsUrl = new HttpGet("https://api.nal.usda.gov/fdc/v1/" + result.fdcId + "?api_key=" + API_KEY);
            CloseableHttpResponse detailsResponse = httpclient.execute(detailsUrl);
            DatabaseSeed.FoodDetails foodDetails = mapper.readValue(EntityUtils.toString(detailsResponse.getEntity()), DatabaseSeed.FoodDetails.class);

            try (Connection connection = SQLiteConnection.getConnection();
                 PreparedStatement foodStatement = connection.prepareStatement(foodQuery);
                 PreparedStatement macronutrientsStatement = connection.prepareStatement(macronutrientsQuery);
                 PreparedStatement micronutrientsStatement = connection.prepareStatement(micronutrientsQuery)) {

                foodStatement.setLong(1, count);
                foodStatement.setString(2, foodDetails.description);

                macronutrientsStatement.setLong(1, count);
                micronutrientsStatement.setLong(1, count);

                for (int i = 2; i < 8; i++) {
                    macronutrientsStatement.setNull(i, java.sql.Types.REAL);
                    micronutrientsStatement.setNull(i, java.sql.Types.REAL);
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

            } catch(SQLException exception) {
                exception.printStackTrace();
            }
            count++;
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


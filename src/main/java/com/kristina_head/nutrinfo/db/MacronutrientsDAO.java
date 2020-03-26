package com.kristina_head.nutrinfo.db;

import com.kristina_head.nutrinfo.api.Macronutrients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MacronutrientsDAO {
    private static Map<String, String> macronutrientMap = new HashMap<>();

    static {
        macronutrientMap.put("saturated_fat", "saturated_fat");
        macronutrientMap.put("polyunsaturated_fat", "polyunsaturated_fat");
        macronutrientMap.put("monounsaturated_fat", "monounsaturated_fat");
        macronutrientMap.put("cholesterol", "cholesterol");
        macronutrientMap.put("fiber", "fiber");
        macronutrientMap.put("sugar", "sugar");
        macronutrientMap.put("protein", "protein");
    }

    public static Macronutrients fetchByFoodId(long id) throws SQLException {
        String macronutrientsQuery = "SELECT food_id, saturated_fat, polyunsaturated_fat, monounsaturated_fat, cholesterol, " +
                                            "fiber, sugar, protein " +
                                     "FROM macronutrients " +
                                     "WHERE food_id = ? ";

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(macronutrientsQuery)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSetToMacronutrients(resultSet);
            }
        }
    }

    public static Collection<Macronutrients> orderByMacronutrient(String macronutrient, int limit, int offset) throws SQLException {
        String macronutrientsQuery = "SELECT * FROM macronutrients " +
                                     "WHERE " + macronutrientMap.get(macronutrient) + " > 0 " +
                                     "ORDER BY " + macronutrientMap.get(macronutrient) + " DESC" +
                                     "LIMIT ? OFFSET ?";

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(macronutrientsQuery)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);

            Collection<Macronutrients> results = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(resultSetToMacronutrients(resultSet));
                }
                return results;
            }
        }
    }

    private static Macronutrients resultSetToMacronutrients(ResultSet resultSet) throws SQLException {
        Long foodId = resultSet.getLong("food_id");
        float saturatedFat = resultSet.getFloat("saturated_fat");
        float polyunsaturatedFat = resultSet.getFloat("polyunsaturated_fat");
        float monounsaturatedFat = resultSet.getFloat("monounsaturated_fat");
        float cholesterol = resultSet.getFloat("cholesterol");
        Macronutrients.Fat fat = new Macronutrients.Fat(saturatedFat, polyunsaturatedFat, monounsaturatedFat, cholesterol);
        float fiber = resultSet.getFloat("fiber");
        float sugar = resultSet.getFloat("sugar");
        Macronutrients.Carbohydrate carbohydrate = new Macronutrients.Carbohydrate(fiber, sugar);
        float protein = resultSet.getFloat("protein");

        return new Macronutrients(foodId, fat, carbohydrate, protein);
    }
}

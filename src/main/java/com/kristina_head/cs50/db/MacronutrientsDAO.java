package com.kristina_head.cs50.db;

import com.kristina_head.cs50.api.Macronutrients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static Macronutrients fetchByFoodId(long id, String macronutrient) throws SQLException {
        String macronutrientsQuery = "SELECT saturated_fat, polyunsaturated_fat, monounsaturated_fat, cholesterol, " +
                                            "fiber, sugar, protein " +
                                     "FROM macronutrients " +
                                     "WHERE food_id = ? " +
                                     "ORDER BY ? DESC";

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(macronutrientsQuery)) {
            statement.setLong(1, id);
            statement.setString(2, macronutrientMap.get(macronutrient));

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSetToMacronutrients(resultSet);
            }
        }
    }

    public static Macronutrients fetchByFoodId(long id) throws SQLException {
        return fetchByFoodId(id, "%");
    }

    private static Macronutrients resultSetToMacronutrients(ResultSet resultSet) throws SQLException {
        float saturatedFat = resultSet.getFloat("saturated_fat");
        float polyunsaturatedFat = resultSet.getFloat("polyunsaturated_fat");
        float monounsaturatedFat = resultSet.getFloat("monounsaturated_fat");
        float cholesterol = resultSet.getFloat("cholesterol");
        Macronutrients.Fat fat = new Macronutrients.Fat(saturatedFat, polyunsaturatedFat, monounsaturatedFat, cholesterol);
        float fiber = resultSet.getFloat("fiber");
        float sugar = resultSet.getFloat("sugar");
        Macronutrients.Carbohydrate carbohydrate = new Macronutrients.Carbohydrate(fiber, sugar);
        float protein = resultSet.getFloat("protein");
        return new Macronutrients(fat, carbohydrate, protein);
    }
}

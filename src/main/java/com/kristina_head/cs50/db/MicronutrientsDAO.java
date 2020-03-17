package com.kristina_head.cs50.db;

import com.kristina_head.cs50.api.Micronutrients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MicronutrientsDAO {
    private static Map<String, String> micronutrientMap = new HashMap<>();

    static {
        micronutrientMap.put("vitamin_a", "vitamin_a");
        micronutrientMap.put("vitamin_c", "vitamin_c");
        micronutrientMap.put("vitamin_d", "vitamin_d");
        micronutrientMap.put("calcium", "calcium");
        micronutrientMap.put("iron", "iron");
        micronutrientMap.put("potassium", "potassium");
        micronutrientMap.put("sodium", "sodium");
    }

    public static Micronutrients fetchByFoodId(long id, String micronutrient) throws SQLException {
        String micronutrientsQuery = "SELECT vitamin_a, vitamin_c, vitamin_d, calcium, iron, potassium, sodium " +
                                     "FROM micronutrients " +
                                     "WHERE food_id = ? " +
                                     "ORDER BY ? DESC";

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(micronutrientsQuery)) {
            statement.setLong(1, id);
            statement.setString(2, micronutrientMap.get(micronutrient));

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSetToMicronutrients(resultSet);
            }
        }
    }

    public static Micronutrients fetchByFoodId(long id) throws SQLException {
        return fetchByFoodId(id, "%");
    }

    private static Micronutrients resultSetToMicronutrients(ResultSet resultSet) throws SQLException {
        float vitaminA = resultSet.getFloat("vitamin_a");
        float vitaminC = resultSet.getFloat("vitamin_c");
        float vitaminD = resultSet.getFloat("vitamin_d");
        float calcium = resultSet.getFloat("calcium");
        float iron = resultSet.getFloat("iron");
        float potassium = resultSet.getFloat("potassium");
        float sodium = resultSet.getFloat("sodium");
        return new Micronutrients(vitaminA, vitaminC, vitaminD, calcium, iron, potassium, sodium);
    }
}

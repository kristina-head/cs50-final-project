package com.kristina_head.nutrinfo.db;

import com.kristina_head.nutrinfo.api.Micronutrients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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

    public static Micronutrients fetchByFoodId(long id) throws SQLException {
        String micronutrientsQuery = "SELECT food_id, vitamin_a, vitamin_c, vitamin_d, calcium, iron, potassium, sodium " +
                                     "FROM micronutrients " +
                                     "WHERE food_id = ?";

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(micronutrientsQuery)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSetToMicronutrients(resultSet);
            }
        }
    }

    public static Collection<Micronutrients> orderByMicronutrient(String micronutrient, int limit, int offset) throws SQLException {
        String micronutrientsQuery = "SELECT * FROM micronutrients " +
                                     "WHERE " + micronutrientMap.get(micronutrient) + " > 0 " +
                                     "ORDER BY " + micronutrientMap.get(micronutrient) + " DESC" +
                                     "LIMIT ? OFFSET ?";

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(micronutrientsQuery)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);

            Collection<Micronutrients> results = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(resultSetToMicronutrients(resultSet));
                }
                return results;
            }
        }
    }

    private static Micronutrients resultSetToMicronutrients(ResultSet resultSet) throws SQLException {
        Long foodId = resultSet.getLong("food_id");
        float vitaminA = resultSet.getFloat("vitamin_a");
        float vitaminC = resultSet.getFloat("vitamin_c");
        float vitaminD = resultSet.getFloat("vitamin_d");
        float calcium = resultSet.getFloat("calcium");
        float iron = resultSet.getFloat("iron");
        float potassium = resultSet.getFloat("potassium");
        float sodium = resultSet.getFloat("sodium");

        return new Micronutrients(foodId, vitaminA, vitaminC, vitaminD, calcium, iron, potassium, sodium);
    }
}

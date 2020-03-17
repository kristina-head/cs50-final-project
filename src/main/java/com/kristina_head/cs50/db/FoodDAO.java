package com.kristina_head.cs50.db;

import com.kristina_head.cs50.api.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO {
    public static List<Food> fetchAll(int limit, int offset, String name) throws SQLException {
        String foodQuery = "SELECT * FROM food WHERE name LIKE ? LIMIT ? OFFSET ?";

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(foodQuery)) {
            statement.setString(1, "%" + name + "%");
            statement.setInt(2, limit);
            statement.setInt(3, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Food> results = new ArrayList<>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    results.add(resultSetToFood(resultSet, id));
                }
                return results;
            }
        }
    }

    public static List<Food> fetchAll(int limit, int offset) throws SQLException {
        return fetchAll(limit, offset, "%");
    }

    public static Food fetchById(long id) throws SQLException {
        String query = "SELECT * FROM food WHERE id = ?";

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSetToFood(resultSet, id);
            }
        }
    }

    private static Food resultSetToFood(ResultSet resultSet, long id) throws SQLException {
        String name = resultSet.getString("name");
        String unit = resultSet.getString("unit");
        int calories = resultSet.getInt("calories");
        return new Food(id, name, unit, calories);
    }
}

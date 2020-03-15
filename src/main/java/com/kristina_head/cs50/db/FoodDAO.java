package com.kristina_head.cs50.db;

import com.kristina_head.cs50.api.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class FoodDAO {
    public static Collection<Food> fetchAll(int limit, int offset) throws SQLException {
        String foodQuery = "SELECT * FROM food LIMIT ? OFFSET ?";

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(foodQuery)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                Collection<Food> results = new ArrayList<>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    results.add(resultSetToFood(resultSet, id));
                }
                return results;
            }
        }
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

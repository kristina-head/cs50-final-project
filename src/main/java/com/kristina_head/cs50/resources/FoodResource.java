package com.kristina_head.cs50.resources;

import com.kristina_head.cs50.api.Food;
import com.kristina_head.cs50.api.Macronutrient;
import com.kristina_head.cs50.api.Micronutrient;
import com.kristina_head.cs50.db.SQLiteConnection;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/food")
@Produces(MediaType.APPLICATION_JSON)
public class FoodResource {

    @GET
    @Path("/all")
    public Response fetchAllFood(@DefaultValue("20") @QueryParam("limit") int limit,
                                 @DefaultValue("0") @QueryParam("offset") int offset) {
        String query = "SELECT * FROM food LIMIT ? OFFSET ?";
        Response response;

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Food> results = new ArrayList<>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    results.add(resultSetToFood(resultSet, id));
                }
                response = Response.ok(results).build();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }

    @GET
    @Path("/{id}")
    public Response fetchFoodById(@PathParam("id") long id) {
        String query = "SELECT * FROM food WHERE id = ?";
        Response response;

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                Food food = resultSetToFood(resultSet, id);
                response = Response.ok(food).build();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }

    @GET
    @Path("/{id}/macronutrients")
    public Response fetchMacronutrientsById(@PathParam("id") long id) {
        String foodQuery = "SELECT * FROM food WHERE id = ?";
        Response response;

        try (Connection connection = SQLiteConnection.getConnection()) {
            Food food = null;

            try (PreparedStatement statement = connection.prepareStatement(foodQuery)) {
                statement.setLong(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    food = resultSetToFood(resultSet, id);
                }
            }
            String macronutrientsQuery = "SELECT saturated_fat, polyunsaturated_fat, monounsaturated_fat, cholesterol, " +
                                                "fiber, sugar, protein FROM macronutrients WHERE food_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(macronutrientsQuery)) {
                statement.setLong(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    food.setMacronutrient(resultSetToMacronutrient(resultSet));
                }
            }
            response = Response.ok(food).build();
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }

    @GET
    @Path("/{id}/macronutrients/micronutrients")
    public Response fetchMicronutrientsById(@PathParam("id") long id) {
        String foodQuery = "SELECT * FROM food WHERE id = ?";
        Response response;

        try (Connection connection = SQLiteConnection.getConnection()) {
            Food food = null;

            try (PreparedStatement statement = connection.prepareStatement(foodQuery)) {
                statement.setLong(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    food = resultSetToFood(resultSet, id);
                }
            }
            String macronutrientsQuery = "SELECT saturated_fat, polyunsaturated_fat, monounsaturated_fat, cholesterol, " +
                                                "fiber, sugar, protein FROM macronutrients WHERE food_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(macronutrientsQuery)) {
                statement.setLong(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    food.setMacronutrient(resultSetToMacronutrient(resultSet));
                }
            }
            String micronutrientsQuery = "SELECT vitamin_a, vitamin_c, vitamin_d, calcium, iron, potassium, sodium " +
                                         "FROM micronutrients WHERE food_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(micronutrientsQuery)) {
                statement.setLong(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    food.setMicronutrient(resultSetToMicronutrient(resultSet));
                }
            }
            response = Response.ok(food).build();
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }

    private Food resultSetToFood(ResultSet resultSet, long id) throws SQLException {
        String name = resultSet.getString("name");
        String unit = resultSet.getString("unit");
        int calories = resultSet.getInt("calories");
        return new Food(id, name, unit, calories);
    }

    private Macronutrient resultSetToMacronutrient(ResultSet resultSet) throws SQLException {
        float saturatedFat = resultSet.getFloat("saturated_fat");
        float polyunsaturatedFat = resultSet.getFloat("polyunsaturated_fat");
        float monounsaturatedFat = resultSet.getFloat("monounsaturated_fat");
        float cholesterol = resultSet.getFloat("cholesterol");
        Macronutrient.Fat fat = new Macronutrient.Fat(saturatedFat, polyunsaturatedFat, monounsaturatedFat, cholesterol);
        float fiber = resultSet.getFloat("fiber");
        float sugar = resultSet.getFloat("sugar");
        Macronutrient.Carbohydrate carbohydrate = new Macronutrient.Carbohydrate(fiber, sugar);
        float protein = resultSet.getFloat("protein");
        return new Macronutrient(fat, carbohydrate, protein);
    }

    private Micronutrient resultSetToMicronutrient(ResultSet resultSet) throws SQLException {
        float vitaminA = resultSet.getFloat("vitamin_a");
        float vitaminC = resultSet.getFloat("vitamin_c");
        float vitaminD = resultSet.getFloat("vitamin_d");
        float calcium = resultSet.getFloat("calcium");
        float iron = resultSet.getFloat("iron");
        float potassium = resultSet.getFloat("potassium");
        float sodium = resultSet.getFloat("sodium");
        return new Micronutrient(vitaminA, vitaminC, vitaminD, calcium, iron, potassium, sodium);
    }
}


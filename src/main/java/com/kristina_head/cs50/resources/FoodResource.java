package com.kristina_head.cs50.resources;

import com.kristina_head.cs50.api.Food;
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
        String query = "SELECT * FROM macro ORDER BY name ASC LIMIT ? OFFSET ?";
        Response response;
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, limit);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Food> results = new ArrayList<>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    results.add(setFood(resultSet, id));
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
        String query = "SELECT * FROM macro WHERE id = ?";
        Response response;
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Food> results = new ArrayList<>();
                while (resultSet.next()) {
                    results.add(setFood(resultSet, id));
                }
                response = Response.ok(results).build();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }

    private Food setFood(ResultSet resultSet, long id) throws SQLException {
        String name = resultSet.getString("name");
        String unit = resultSet.getString("unit");
        int calories = resultSet.getInt("calories");
        float saturatedFat = resultSet.getFloat("saturated_fat");
        float polyunsaturatedFat = resultSet.getFloat("polyunsaturated_fat");
        float monounsaturatedFat = resultSet.getFloat("monounsaturated_fat");
        Food.Fat fat = new Food.Fat(saturatedFat, polyunsaturatedFat, monounsaturatedFat);
        float fiber = resultSet.getFloat("fiber");
        float sugar = resultSet.getFloat("sugar");
        Food.Carbohydrate carbohydrate = new Food.Carbohydrate(fiber, sugar);
        float protein = resultSet.getFloat("protein");
        return new Food(id, name, unit, calories, fat, carbohydrate, protein);
    }
}


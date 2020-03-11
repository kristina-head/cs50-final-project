package com.kristina_head.cs50.resources;

import com.kristina_head.cs50.api.Food;
import com.kristina_head.cs50.db.SQLiteConnection;

import javax.ws.rs.*;
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
    public Response fetchAllFood(@DefaultValue("20") @QueryParam("limit") int limit, @DefaultValue("0") @QueryParam("offset") int offset) {
        String query = "SELECT * FROM food ORDER BY name ASC LIMIT ? OFFSET ?";
        Response response;
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, limit);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Food> results = new ArrayList<>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    int calories = resultSet.getInt("calories");
                    String unit = resultSet.getString("unit");
                    Food food = new Food(id, name, calories, unit);
                    results.add(food);
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
                List<Food> results = new ArrayList<>();
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int calories = resultSet.getInt("calories");
                    String unit = resultSet.getString("unit");
                    Food food = new Food(id, name, calories, unit);
                    results.add(food);
                }
                response = Response.ok(results).build();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }
}


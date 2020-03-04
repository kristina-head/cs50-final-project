package com.kristina_head.cs50.resources;

import com.kristina_head.cs50.api.Food;
import com.kristina_head.cs50.db.SQLiteConnection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Path("food")
@Produces(MediaType.APPLICATION_JSON)
public class FoodResource {

    @GET
    public Response allFood() {
        String query = "SELECT * FROM food";
        Response response;
        try (Connection connection = SQLiteConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            List<Food> results = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int calories = resultSet.getInt("calories");
                String unit = resultSet.getString("unit");
                Food food = new Food(name, calories, unit);
                results.add(food);
            }
            response = Response.ok(results).build();
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }
}


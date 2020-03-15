package com.kristina_head.cs50.resources;

import com.kristina_head.cs50.api.Food;
import com.kristina_head.cs50.api.Macronutrients;
import com.kristina_head.cs50.api.Micronutrients;
import com.kristina_head.cs50.db.FoodDAO;
import com.kristina_head.cs50.db.MacronutrientsDAO;
import com.kristina_head.cs50.db.SQLiteConnection;

import javax.crypto.Mac;
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
import java.util.Collection;
import java.util.List;

@Path("/food")
@Produces(MediaType.APPLICATION_JSON)
public class FoodResource {

    @GET
    @Path("/all")
    public Response fetchAllFood(@DefaultValue("20") @QueryParam("limit") int limit,
                                 @DefaultValue("0") @QueryParam("offset") int offset) {
        Response response;
        try {
            Collection<Food> results = FoodDAO.fetchAll(limit, offset);
            response = Response.ok(results).build();
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }

    @GET
    @Path("/all/macronutrients")
    public Response fetchAllMacronutrients(@DefaultValue("20") @QueryParam("limit") int limit,
                                           @DefaultValue("0") @QueryParam("offset") int offset) {
        Response response;
        try {
            Collection<Food> results = FoodDAO.fetchAll(limit, offset);

            for (Food food : results) {
                Macronutrients macronutrients = MacronutrientsDAO.fetchByFoodId(food.getId());
                food.setMacronutrients(macronutrients);
            }

            response = Response.ok(results).build();
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }

//    @GET
//    @Path("/all/macronutrients/micronutrients")
//    TODO

    @GET
    @Path("/{id}")
    public Response fetchFoodById(@PathParam("id") long id) {
        Response response;
        try {
            Food food = FoodDAO.fetchById(id);
            response = Response.ok(food).build();
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }

    @GET
    @Path("/{id}/macronutrients")
    public Response fetchMacronutrientsById(@PathParam("id") long id) {
        Response response;
        try {
            Food food = FoodDAO.fetchById(id);
            Macronutrients macronutrients = MacronutrientsDAO.fetchByFoodId(id);
            food.setMacronutrients(macronutrients);
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
                    food.setMacronutrients(resultSetToMacronutrients(resultSet));
                }
            }
            String micronutrientsQuery = "SELECT vitamin_a, vitamin_c, vitamin_d, calcium, iron, potassium, sodium " +
                                         "FROM micronutrients WHERE food_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(micronutrientsQuery)) {
                statement.setLong(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    food.setMicronutrients(resultSetToMicronutrients(resultSet));
                }
            }
            response = Response.ok(food).build();
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }



    private Micronutrients resultSetToMicronutrients(ResultSet resultSet) throws SQLException {
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


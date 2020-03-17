package com.kristina_head.cs50.resources;

import com.kristina_head.cs50.api.Food;
import com.kristina_head.cs50.api.Macronutrients;
import com.kristina_head.cs50.api.Micronutrients;
import com.kristina_head.cs50.db.FoodDAO;
import com.kristina_head.cs50.db.MacronutrientsDAO;
import com.kristina_head.cs50.db.MicronutrientsDAO;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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

    @GET
    @Path("/all/macronutrients/micronutrients")
    public Response fetchAllMicronutrients(@DefaultValue("20") @QueryParam("limit") int limit,
                                           @DefaultValue("0") @QueryParam("offset") int offset) {
        Response response;
        try {
            Collection<Food> results = FoodDAO.fetchAll(limit, offset);

            for (Food food : results) {
                Macronutrients macronutrients = MacronutrientsDAO.fetchByFoodId(food.getId());
                food.setMacronutrients(macronutrients);
                Micronutrients micronutrients = MicronutrientsDAO.fetchByFoodId(food.getId());
                food.setMicronutrients(micronutrients);
            }

            response = Response.ok(results).build();
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }

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
        Response response;
        try {
            Food food = FoodDAO.fetchById(id);
            Macronutrients macronutrients = MacronutrientsDAO.fetchByFoodId(id);
            food.setMacronutrients(macronutrients);
            Micronutrients micronutrients = MicronutrientsDAO.fetchByFoodId(id);
            food.setMicronutrients(micronutrients);
            response = Response.ok(food).build();
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }

    @GET
    @Path("/search/name")
    public Response searchByName(@DefaultValue("20") @QueryParam("limit") int limit,
                           @DefaultValue("0") @QueryParam("offset") int offset,
                           @DefaultValue("%") @QueryParam("q") String name) {

        Response response;
        try {
            Collection<Food> results = FoodDAO.fetchAll(limit, offset, name);
            for (Food food : results) {
                Macronutrients macronutrients = MacronutrientsDAO.fetchByFoodId(food.getId());
                food.setMacronutrients(macronutrients);
                Micronutrients micronutrients = MicronutrientsDAO.fetchByFoodId(food.getId());
                food.setMicronutrients(micronutrients);
            }
            response = Response.ok(results).build();
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }

    @GET
    @Path("/search/macronutrient")
    public Response filterByMacronutrient(@DefaultValue("20") @QueryParam("limit") int limit,
                           @DefaultValue("0") @QueryParam("offset") int offset,
                           @DefaultValue("%") @QueryParam("q") String macronutrient) {
        Response response;
        try {
            Collection<Macronutrients> macronutrients = MacronutrientsDAO.orderByMacronutrient(macronutrient);
            Collection<Food> results = new ArrayList<>(macronutrients.size());
            for (Macronutrients nutrient : macronutrients) {
                Food food = FoodDAO.fetchById(nutrient.getFoodId());
                food.setMacronutrients(nutrient);
                results.add(food);
            }
            response = Response.ok(results).build();
        } catch (SQLException exception) {
            exception.printStackTrace();
            response = Response.serverError().build();
        }
        return response;
    }

    @GET
    @Path("/search/micronutrient")
    public Response filterByMicronutrient(@DefaultValue("20") @QueryParam("limit") int limit,
                           @DefaultValue("0") @QueryParam("offset") int offset,
                           @DefaultValue("%") @QueryParam("q") String micronutrient) {
        Response response;
        try {
            Collection<Micronutrients> micronutrients = MicronutrientsDAO.orderByMicronutrient(micronutrient);
            Collection<Food> results = new ArrayList<>(micronutrients.size());
            for (Micronutrients nutrient : micronutrients) {
                Food food = FoodDAO.fetchById(nutrient.getFoodId());
                food.setMicronutrients(nutrient);
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


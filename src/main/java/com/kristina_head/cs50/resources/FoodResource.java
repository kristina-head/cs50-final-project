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
import java.util.Collection;

@Path("/food")
@Produces(MediaType.APPLICATION_JSON)
public class FoodResource {

    @GET
    @Path("/all")
    public Response fetchAllFood(@DefaultValue("20") @QueryParam("limit") int limit,
                                 @DefaultValue("0") @QueryParam("offset") int offset,
                                 @DefaultValue("%") @QueryParam("name") String name) {
        Response response;
        try {
            Collection<Food> results = FoodDAO.fetchAll(limit, offset, name);
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
                                           @DefaultValue("0") @QueryParam("offset") int offset,
                                           @DefaultValue("%") @QueryParam("name") String name) {
        Response response;
        try {
            Collection<Food> results = FoodDAO.fetchAll(limit, offset, name);

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
                                           @DefaultValue("0") @QueryParam("offset") int offset,
                                           @DefaultValue("%") @QueryParam("name") String name) {
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
}


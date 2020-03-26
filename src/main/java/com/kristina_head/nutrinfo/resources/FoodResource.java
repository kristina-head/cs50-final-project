package com.kristina_head.nutrinfo.resources;

import com.kristina_head.nutrinfo.api.Food;
import com.kristina_head.nutrinfo.api.Macronutrients;
import com.kristina_head.nutrinfo.api.Micronutrients;
import com.kristina_head.nutrinfo.db.FoodDAO;
import com.kristina_head.nutrinfo.db.MacronutrientsDAO;
import com.kristina_head.nutrinfo.db.MicronutrientsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(FoodResource.class);

    @GET
    @Path("/all")
    public Response fetchAllFood(@DefaultValue("20") @QueryParam("limit") int limit,
                                 @DefaultValue("0") @QueryParam("offset") int offset) {
        Response response;
        try {
            Collection<Food> results = FoodDAO.fetchAll(limit, offset);
            response = Response.ok(results).build();
        } catch (SQLException exception) {
            logger.error("Something went wrong whilst fetching all food", exception);
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
            logger.error("Something went wrong whilst fetching all macronutrients", exception);
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
            logger.error("Something went wrong whilst fetching all micronutrients", exception);
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
            logger.error("Something went wrong whilst fetching the food by id", exception);
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
            logger.error("Something went wrong whilst fetching the macronutrients by id", exception);
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
            logger.error("Something went wrong whilst fetching the micronutrients by id", exception);
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
            logger.error("Something went wrong whilst searching food by name", exception);
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
            Collection<Macronutrients> macronutrients = MacronutrientsDAO.orderByMacronutrient(macronutrient, limit, offset);
            Collection<Food> results = new ArrayList<>(macronutrients.size());
            for (Macronutrients nutrient : macronutrients) {
                Food food = FoodDAO.fetchById(nutrient.getFoodId());
                food.setMacronutrients(nutrient);
                Micronutrients micronutrients = MicronutrientsDAO.fetchByFoodId(food.getId());
                food.setMicronutrients(micronutrients);
                results.add(food);
            }
            response = Response.ok(results).build();
        } catch (SQLException exception) {
            logger.error("Something went wrong whilst filtering food by macronutrient", exception);
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
            Collection<Micronutrients> micronutrients = MicronutrientsDAO.orderByMicronutrient(micronutrient, limit, offset);
            Collection<Food> results = new ArrayList<>(micronutrients.size());
            for (Micronutrients nutrient : micronutrients) {
                Food food = FoodDAO.fetchById(nutrient.getFoodId());
                food.setMicronutrients(nutrient);
                Macronutrients macronutrients = MacronutrientsDAO.fetchByFoodId(food.getId());
                food.setMacronutrients(macronutrients);
                results.add(food);
            }
            response = Response.ok(results).build();
        } catch (SQLException exception) {
            logger.error("Something went wrong whilst filtering food by micronutruent", exception);
            response = Response.serverError().build();
        }
        return response;
    }
}


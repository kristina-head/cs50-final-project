package com.kristina_head.cs50.resources;

import com.kristina_head.cs50.api.Food;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("foods")
@Produces(MediaType.APPLICATION_JSON)
public class FoodResource {

    @GET
    public List<Food> allFoods() {
        // TODO: actually return all foods in db
        Food f = new Food("Avocado", 160, "grams");
        return Collections.singletonList(f);
    }
}

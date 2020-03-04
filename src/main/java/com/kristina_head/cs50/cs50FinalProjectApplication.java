package com.kristina_head.cs50;

import com.kristina_head.cs50.resources.FoodResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class cs50FinalProjectApplication extends Application<cs50FinalProjectConfiguration> {

    public static void main(final String[] args) throws Exception {
        new cs50FinalProjectApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<cs50FinalProjectConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final cs50FinalProjectConfiguration configuration,
                    final Environment environment) {
        FoodResource foodResource = new FoodResource();
        environment.jersey().register(foodResource);
    }
}

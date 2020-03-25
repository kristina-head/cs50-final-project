package com.kristina_head.nutrinfo;

import com.kristina_head.nutrinfo.resources.FoodResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class NutrinfoApplication extends Application<NutrinfoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new NutrinfoApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<NutrinfoConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final NutrinfoConfiguration configuration,
                    final Environment environment) {
        FoodResource foodResource = new FoodResource();
        environment.jersey().register(foodResource);
    }
}

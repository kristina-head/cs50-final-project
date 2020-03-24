package com.kristina_head.cs50;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

public class DatabaseSeedTest {
    String foodSearchResponse = "{\n" +
            "  \"foodSearchCriteria\": {\n" +
            "    \"includeDataTypeList\": [\n" +
            "      \"SR Legacy\"\n" +
            "    ],\n" +
            "    \"generalSearchInput\": \"raw\",\n" +
            "    \"pageNumber\": 3,\n" +
            "    \"requireAllWords\": false\n" +
            "  },\n" +
            "  \"totalHits\": 1388,\n" +
            "  \"currentPage\": 3,\n" +
            "  \"totalPages\": 28,\n" +
            "  \"foods\": [\n" +
            "    {\n" +
            "      \"fdcId\": 169977,\n" +
            "      \"description\": \"Cabbage, red, raw\",\n" +
            "      \"scientificName\": \"Brassica oleracea (Capitata Group)\",\n" +
            "      \"dataType\": \"SR Legacy\",\n" +
            "      \"ndbNumber\": \"11112\",\n" +
            "      \"publishedDate\": \"2019-04-01\",\n" +
            "      \"allHighlightFields\": \"\",\n" +
            "      \"score\": 274.08264\n" +
            "    },\n" +
            "    {\n" +
            "      \"fdcId\": 170388,\n" +
            "      \"description\": \"Cabbage, savoy, raw\",\n" +
            "      \"scientificName\": \"Brassica oleracea (Capitata Group)\",\n" +
            "      \"dataType\": \"SR Legacy\",\n" +
            "      \"ndbNumber\": \"11114\",\n" +
            "      \"publishedDate\": \"2019-04-01\",\n" +
            "      \"allHighlightFields\": \"\",\n" +
            "      \"score\": 274.08264\n" +
            "    },\n" +
            "    {\n" +
            "      \"fdcId\": 171715,\n" +
            "      \"description\": \"Carambola, (starfruit), raw\",\n" +
            "      \"scientificName\": \"Averrhoa carambola\",\n" +
            "      \"dataType\": \"SR Legacy\",\n" +
            "      \"ndbNumber\": \"9060\",\n" +
            "      \"publishedDate\": \"2019-04-01\",\n" +
            "      \"allHighlightFields\": \"\",\n" +
            "      \"score\": 274.08264\n" +
            "    }]}";

    String foodDetailsResponse = "{\n" +
            "  \"foodClass\": \"FinalFood\",\n" +
            "  \"description\": \"Apricots, raw\",\n" +
            "  \"foodNutrients\": [\n" +
            "    {\n" +
            "      \"type\": \"FoodNutrient\",\n" +
            "      \"nutrient\": {\n" +
            "        \"id\": 2045,\n" +
            "        \"number\": \"951\",\n" +
            "        \"name\": \"Proximates\",\n" +
            "        \"rank\": 50,\n" +
            "        \"unitName\": \"g\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"FoodNutrient\",\n" +
            "      \"id\": 1632158,\n" +
            "      \"nutrient\": {\n" +
            "        \"id\": 1051,\n" +
            "        \"number\": \"255\",\n" +
            "        \"name\": \"Water\",\n" +
            "        \"rank\": 100,\n" +
            "        \"unitName\": \"g\"\n" +
            "      },\n" +
            "      \"dataPoints\": 26,\n" +
            "      \"amount\": 86.35\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"FoodNutrient\",\n" +
            "      \"id\": 1632157,\n" +
            "      \"nutrient\": {\n" +
            "        \"id\": 1008,\n" +
            "        \"number\": \"208\",\n" +
            "        \"name\": \"Energy\",\n" +
            "        \"rank\": 300,\n" +
            "        \"unitName\": \"kcal\"\n" +
            "      },\n" +
            "      \"dataPoints\": 0,\n" +
            "      \"foodNutrientDerivation\": {\n" +
            "        \"id\": 49,\n" +
            "        \"code\": \"NC\",\n" +
            "        \"description\": \"Calculated\",\n" +
            "        \"foodNutrientSource\": {\n" +
            "          \"id\": 2,\n" +
            "          \"code\": \"4\",\n" +
            "          \"description\": \"Calculated or imputed\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"amount\": 48\n" +
            "    }]},";

    @Test
    public void deserializeFoodSearchJSON() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();

        DatabaseSeed.FoodSearch foodSearch = mapper.readValue(foodSearchResponse, DatabaseSeed.FoodSearch.class);
        System.out.println(foodSearch);
    }

    @Test
    public void deserializeFoodDetailsJSON() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();

        DatabaseSeed.FoodDetails foodDetails = mapper.readValue(foodDetailsResponse, DatabaseSeed.FoodDetails.class);
        System.out.println(foodDetails);
    }
}

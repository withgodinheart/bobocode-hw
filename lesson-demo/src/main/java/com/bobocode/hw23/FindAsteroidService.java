package com.bobocode.hw23;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.StreamSupport;

public class FindAsteroidService {

    public static final String URL = "https://api.nasa.gov/neo/rest/v1/feed";
    public static final String KEY = "DEMO_KEY";
    public static final String DATE = LocalDate.now().toString();
    public static final RestTemplate rt = new RestTemplate();

    public static void main(String[] args) {
        var node = rt.getForObject(buildUrl(), JsonNode.class);
        var asteroids = node.get("near_earth_objects").get(DATE);
        var biggest = findBiggest(asteroids);
        System.out.println(biggest.get("name"));
    }

    private static JsonNode findBiggest(JsonNode asteroids) {
        return StreamSupport.stream(asteroids.spliterator(), false)
                .max(Comparator.comparingDouble(el -> {
                            var value = el.get("estimated_diameter").get("meters");
                            var min = value.get("estimated_diameter_min").asDouble();
                            var max = value.get("estimated_diameter_max").asDouble();
                            return (min + max) / 2;
                        }
                )).orElseThrow();
    }

    private static String buildUrl() {
        return UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("api_key", KEY)
                .queryParam("start_date", DATE)
                .toUriString();
    }
}

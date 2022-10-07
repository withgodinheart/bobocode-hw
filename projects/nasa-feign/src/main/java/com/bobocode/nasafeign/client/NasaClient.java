package com.bobocode.nasafeign.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "nasa-service", url = "${nasa.api.url}")
public interface NasaClient {

    @GetMapping("${nasa.api.path}")
    JsonNode find(@RequestParam(name = "api_key") String apiKey,
                  @RequestParam Integer sol,
                  @RequestParam(required = false) String camera);
}

package com.bobocode.hw21.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class PictureService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${nasa.api.url}")
    private String nasaApiUrl;

    @Value("${nasa.api.key}")
    private String nasaApiKey;

    @SneakyThrows
    public Object run(int sol) {
        URI url = UriComponentsBuilder.fromHttpUrl(nasaApiUrl)
                .queryParam("api_key", nasaApiKey)
                .queryParam("sol", sol)
                .build()
                .toUri();

        log.info("URL: {}", url.toString());

        return restTemplate.getForObject(url, JsonNode.class);
//        return restTemplate.exchange(url, HttpMethod.GET, null, JsonNode.class);
    }
}

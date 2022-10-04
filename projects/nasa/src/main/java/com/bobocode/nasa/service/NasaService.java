package com.bobocode.nasa.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class NasaService {

    @Value("${nasa.api.url}")
    private String url;

    @Value("${nasa.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Cacheable("img")
    public byte[] run(Integer sol, Optional<String> camera) {
        var json = restTemplate.getForObject(buildUrl(sol, camera), JsonNode.class);
        var urls = getUriList(json);
        var largestImgUri = getLargestImg(urls);

        return restTemplate.getForObject(largestImgUri, byte[].class);
    }

    @Cacheable("img_uri")
    public URI getUri(Integer sol, Optional<String> camera) {
        var json = restTemplate.getForObject(buildUrl(sol, camera), JsonNode.class);
        var urls = getUriList(json);

        return getLargestImg(urls);
    }

    private List<String> getUriList(JsonNode json) {
        return StreamSupport.stream(json.get("photos").spliterator(), false)
                .map(node -> node.get("img_src").asText())
                .toList();
    }

    private URI getLargestImg(List<String> list) {
        return list.parallelStream()
                .map(restTemplate::headForHeaders)
                .map(HttpHeaders::getLocation)
                .map(redirectedUri -> {
                    var size = restTemplate.headForHeaders(redirectedUri).getContentLength();
                    return Map.entry(redirectedUri, size);
                })
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    private String buildUrl(Integer sol, Optional<String> camera) {
        return UriComponentsBuilder.fromHttpUrl(this.url)
                .queryParam("api_key", apiKey)
                .queryParam("sol", sol)
                .queryParamIfPresent("camera", camera)
                .toUriString();
    }
}

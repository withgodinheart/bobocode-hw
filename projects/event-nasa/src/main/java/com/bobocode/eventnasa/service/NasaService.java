package com.bobocode.eventnasa.service;

import com.bobocode.eventnasa.dto.Request;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class NasaService {

    private final RestTemplate restTemplate;

    @Value("${nasa.api.url}")
    private String url;

    @Value("${nasa.api.key}")
    private String key;

    public String findPicture(Request request) {
        var fullUrl = buildUrl(request.sol(), Optional.ofNullable(request.camera()));
        log.info("***** FULL-URL: {}", fullUrl);
        var node = restTemplate.getForObject(fullUrl, JsonNode.class);
        var list = getUrlsList(node);
        log.info("***** LIST: {}", list);
        var largest = getLargestPictureUrl(list);
        log.info("***** LARGEST: {}", largest);
        return largest;
    }

    private List<String> getUrlsList(@NonNull JsonNode node) {
        return StreamSupport.
                stream(node.get("photos").spliterator(), false)
                .map(el -> el.get("img_src"))
                .map(JsonNode::asText)
                .toList();
    }

    private String getLargestPictureUrl(List<String> list) {
        return list.parallelStream()
                .map(restTemplate::headForHeaders)
                .map(HttpHeaders::getLocation)
                .map(this::headForSize)
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    private Map.Entry<String, Long> headForSize(URI redirectUrl) {
        var size = restTemplate.headForHeaders(redirectUrl).getContentLength();
        return Map.entry(redirectUrl.toString(), size);
    }

    private String buildUrl(Integer sol, Optional<String> camera) {
        return UriComponentsBuilder
                .fromHttpUrl(this.url)
                .queryParam("api_key", this.key)
                .queryParam("sol", sol)
                .queryParamIfPresent("camera", camera)
                .build()
                .toUriString();
    }
}

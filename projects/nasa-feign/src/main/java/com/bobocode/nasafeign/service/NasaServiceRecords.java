package com.bobocode.nasafeign.service;

import com.bobocode.nasafeign.client.NasaClientRecords;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class NasaServiceRecords {

    private final NasaClientRecords nasaClient;
    private final RestTemplate restTemplate;

    @Value("${nasa.api.key}")
    private String apiKey;

    @Cacheable("mars")
    public ResponseEntity<byte[]> run(Integer sol, String camera) {
        Objects.requireNonNull(sol);
        var response = nasaClient.find(apiKey, sol, camera);
        var largestUrl = findLargestUrl(response);

        return restTemplate.exchange(largestUrl, HttpMethod.GET, null, byte[].class);
    }

    private URI findLargestUrl(NasaClientRecords.Photos response) {
        return response.photos().stream().parallel()
                .map(NasaClientRecords.Photo::ImgSrc)
                .map(restTemplate::headForHeaders)
                .map(HttpHeaders::getLocation)
                .map(redirectedUrl -> {
                    Objects.requireNonNull(redirectedUrl);
                    var size = restTemplate.headForHeaders(redirectedUrl).getContentLength();
                    return Map.entry(redirectedUrl, size);
                }).max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();
    }
}

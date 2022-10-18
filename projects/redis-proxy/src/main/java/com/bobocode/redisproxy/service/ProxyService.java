package com.bobocode.redisproxy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProxyService {

    private final RestTemplate restTemplate;
    public static final Integer PORT = 8080;

    @Cacheable("redis")
    public byte[] proxy(Integer sol, String camera) {
        var url = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .port(PORT)
                .queryParam("sol", sol)
                .queryParamIfPresent("camera", Optional.ofNullable(camera))
                .build().toUriString();

        log.info("***** URL: {}", url);
        return restTemplate.getForObject(url, byte[].class);
    }

    private final void addCameraParam(UriComponentsBuilder builder, String camera) {
        if (camera != null) {
            builder.replaceQueryParam("camera", camera);
        }
    }
}

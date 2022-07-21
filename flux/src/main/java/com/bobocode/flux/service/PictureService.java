package com.bobocode.flux.service;

import com.bobocode.flux.model.Picture;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class PictureService {

    @Value("${nasa.api.url}")
    private String nasaApiUrl;

    @Value("${nasa.api.key}")
    private String nasaApiKey;

    public Mono<String> getLargestPicture(final int sol) {
        return WebClient.create(prepareFullUrl(sol)).get()
                .exchangeToMono(response -> response.bodyToMono(JsonNode.class))
                .map(this::fetchPictureUrls)
                .flatMapMany(Flux::fromIterable)
                .flatMap(this::getRedirectedUrl)
                .flatMap(this::fetchSize)
                .reduce((p1, p2) -> p1.size() > p2.size() ? p1 : p2)
                .map(Picture::url);
    }

    private List<String> fetchPictureUrls(final JsonNode jsonNode) {
        return StreamSupport.stream(jsonNode.get("photos").spliterator(), false)
                .map(p -> p.get("img_src"))
                .map(JsonNode::asText)
                .toList();
    }

    private Mono<String> getRedirectedUrl(final String pictureUrl) {
        return WebClient.create(pictureUrl)
                .head()
                .exchangeToMono(ClientResponse::toBodilessEntity)
                .map(HttpEntity::getHeaders)
                .map(HttpHeaders::getLocation)
                .map(URI::toString);
    }

    private Mono<Picture> fetchSize(final String redirectedUrl) {
        return WebClient.create(redirectedUrl)
                .head()
                .exchangeToMono(ClientResponse::toBodilessEntity)
                .map(HttpEntity::getHeaders)
                .map(HttpHeaders::getContentLength)
                .map(size -> new Picture(redirectedUrl, size));
    }

    private String prepareFullUrl(final int sol) {
        return UriComponentsBuilder.fromHttpUrl(nasaApiUrl)
                .queryParam("api_key", nasaApiKey)
                .queryParam("sol", sol)
                .build()
                .toUriString();
    }
}

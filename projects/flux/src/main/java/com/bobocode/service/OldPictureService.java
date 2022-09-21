package com.bobocode.service;

import com.bobocode.model.Picture;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class OldPictureService {

    @Value("${nasa.api.url}")
    private String nasaApiUrl;

    @Value("${nasa.api.key}")
    private String nasaApiKey;

    public Mono<String> getLargestPicture(final int sol) {
        return WebClient.create(nasaApiUrl)
                .get()
                .uri(builder -> builder
                        .queryParam("api_key", nasaApiKey)
                        .queryParam("sol", sol)
                        .build()
                )
                .exchangeToMono(response -> response.bodyToMono(JsonNode.class))
                .map(jsonNode -> jsonNode.get("photos"))
                .flatMapMany(Flux::fromIterable)
                .map(jsonNode -> jsonNode.get("img_src"))
                .map(JsonNode::asText)
                .flatMap(pictureUrl -> WebClient.create(pictureUrl)
                        .head()
                        .exchangeToMono(ClientResponse::toBodilessEntity)
                        .map(HttpEntity::getHeaders)
                        .map(HttpHeaders::getLocation)
                        .map(URI::toString)
                        .flatMap(redirectedUrl -> WebClient.create(redirectedUrl)
                                .head()
                                .exchangeToMono(ClientResponse::toBodilessEntity)
                                .map(HttpEntity::getHeaders)
                                .map(HttpHeaders::getContentLength)
                                .map(size -> new Picture(redirectedUrl, size))
                        )
                )
                .reduce((p1, p2) -> p1.size() > p2.size() ? p1 : p2)
                .map(Picture::url);
    }
}

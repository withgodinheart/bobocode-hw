package com.bobocode.rabbitnasa.service;

import com.bobocode.rabbitnasa.dto.CommandDto;
import com.bobocode.rabbitnasa.dto.RequestDto;
import com.bobocode.rabbitnasa.storage.Storage;
import com.bobocode.rabbitnasa.util.ValidationUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

import static com.bobocode.rabbitnasa.config.RabbitConfig.QUEUE_NAME;
import static com.bobocode.rabbitnasa.util.ValidationUtil.throwRandomRuntimeException;

@Service
@RequiredArgsConstructor
@Slf4j
public class NasaService {

    private final Storage storage;
    private final RestTemplate restTemplate;

    @Value("${nasa.api.url}")
    private String url;

    @Value("${nasa.api.key}")
    private String key;

    public String generateId(RequestDto request) {
        ValidationUtil.validateCommand(request);
        log.info("Request was validated: {}", request);

        return RandomStringUtils.randomAlphabetic(5);
    }

    @Cacheable("largest")
    public byte[] getPicture(String commandId) {
        return storage.get(commandId).orElseThrow();
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void processRequest(CommandDto command) {
        throwRandomRuntimeException();
        var img = findPicture(command.request());
        storage.put(command.id(), img);
        log.info("Image was put into storage with key: {}", command.id());
    }

    private byte[] findPicture(RequestDto request) {
        var fullUrl = buildUrl(request.getSol(), Optional.ofNullable(request.getCamera()));
        var node = restTemplate.getForObject(fullUrl, JsonNode.class);
        var list = getUrlsList(node);
        var largestUrl = getLargestPictureUrl(list);
        log.info("Largest picture's url: {}", largestUrl);

        return restTemplate.getForObject(largestUrl, byte[].class);
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

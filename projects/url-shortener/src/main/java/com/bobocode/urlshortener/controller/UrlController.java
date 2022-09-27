package com.bobocode.urlshortener.controller;

import com.bobocode.urlshortener.dto.UrlDto;
import com.bobocode.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/short")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<?> generateShortUrl(@RequestBody UrlDto dto) {
        var entity = urlService.shorten(dto);
        return ResponseEntity
                .created(buildURIFrom(entity.getId()))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> openUrl(@PathVariable String id) {
        var uri = urlService.findById(id);

        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(uri))
                .build();
    }

    private URI buildURIFrom(String key) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(key).build()
                .toUri();
    }
}

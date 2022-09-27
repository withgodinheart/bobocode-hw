package com.bobocode.urlshortener.service;

import com.bobocode.urlshortener.dto.UrlDto;
import com.bobocode.urlshortener.entity.Url;
import com.bobocode.urlshortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public Url shorten(UrlDto dto) {
        var entity = new Url();
        entity.setOriginalUrl(dto.url());
        entity.setTitle(dto.title());

        return urlRepository.save(entity);
    }

    @Cacheable("urls")
    public String findById(String id) {
        return urlRepository.findById(id).
                map(Url::getOriginalUrl)
                .orElseThrow(
                        () -> new IllegalArgumentException("No entity with id = %s".formatted(id)));
    }
}

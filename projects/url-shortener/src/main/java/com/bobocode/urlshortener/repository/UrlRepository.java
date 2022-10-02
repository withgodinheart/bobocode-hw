package com.bobocode.urlshortener.repository;

import com.bobocode.urlshortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, String> {
}

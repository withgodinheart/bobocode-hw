package com.bobocode.urlshortener.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "urls")
public class Url {

    @Id
    @GenericGenerator(name = "short_url", strategy = "com.bobocode.urlshortener.entity.generator.ShortUrlGenerator")
    @GeneratedValue(generator = "short_url")
    private String id;

    @Column(name = "url", nullable = false, updatable = false)
    private String originalUrl;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;
}

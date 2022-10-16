package com.bobocode.rabbitnasa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;

@Cacheable
@SpringBootApplication
public class RabbitNasaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitNasaApplication.class, args);
    }

}

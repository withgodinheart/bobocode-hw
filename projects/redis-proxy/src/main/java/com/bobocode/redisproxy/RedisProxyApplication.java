package com.bobocode.redisproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisProxyApplication.class, args);
    }

}

package com.bobocode.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class BootApplication {

    public static void main(String[] args) {
        var run = SpringApplication.run(BootApplication.class, args);
        System.out.println(run.getBean("testBean"));
    }

}

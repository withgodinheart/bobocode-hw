package com.bobocode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BootApplication {

    public static void main(String[] args) {
        var run = SpringApplication.run(BootApplication.class, args);
//        System.out.println(run.getBean("testBean"));
    }

}

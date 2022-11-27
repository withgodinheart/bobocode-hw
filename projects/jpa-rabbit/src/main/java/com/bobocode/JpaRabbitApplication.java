package com.bobocode;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class JpaRabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaRabbitApplication.class, args);
    }

}

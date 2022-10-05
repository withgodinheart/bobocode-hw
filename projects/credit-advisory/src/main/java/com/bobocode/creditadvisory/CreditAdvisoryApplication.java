package com.bobocode.creditadvisory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class CreditAdvisoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditAdvisoryApplication.class, args);
    }

}

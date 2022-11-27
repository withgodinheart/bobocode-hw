package com.bobocode.my.enableconfig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateLogConfiguration {

    @Bean
    public DateLogBeanPostProcessor dateLogBeanPostProcessor() {
        return new DateLogBeanPostProcessor();
    }
}

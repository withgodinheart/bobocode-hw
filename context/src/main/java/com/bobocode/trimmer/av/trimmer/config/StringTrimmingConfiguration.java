package com.bobocode.trimmer.av.trimmer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringTrimmingConfiguration {

    @Bean
    public TrimmedAnnotationBeanPostProcessor trimPostProcessor() {
        return new TrimmedAnnotationBeanPostProcessor();
    }
}

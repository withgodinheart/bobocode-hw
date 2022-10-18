package com.bobocode.eventnasa.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "oleksandr-vashchenko-picture-request-queue";
    public static final String EXCHANGE_NAME = "picture-request-fanout";
    public static final String RESULT_QUEUE_NAME = "picture-result-queue";

    @Bean
    public Queue myQueue() {
        return QueueBuilder
                .nonDurable(QUEUE_NAME)
                .autoDelete()
                .build();
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder
                .fanoutExchange(EXCHANGE_NAME)
                .build();
    }

    @Bean
    public Binding bindMyQueue() {
        return BindingBuilder
                .bind(myQueue())
                .to(exchange())
                .with("")
                .noargs();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

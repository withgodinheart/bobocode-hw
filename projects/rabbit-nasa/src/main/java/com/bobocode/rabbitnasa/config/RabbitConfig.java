package com.bobocode.rabbitnasa.config;

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

    public static final String QUEUE_NAME = "largest-picture-command-queue";
    private static final String DLQ_NAME = "largest-picture-command-dlq";
    public static final String EXCHANGE_NAME = "largest-picture-command-exchange";

    @Bean
    public Queue queue() {
        return QueueBuilder
                .durable(QUEUE_NAME)
                .deadLetterExchange("")
                .deadLetterRoutingKey(DLQ_NAME)
                .build();
    }

    @Bean
    public Queue dlq() {
        return QueueBuilder
                .durable(DLQ_NAME)
                .build();
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.
                directExchange(EXCHANGE_NAME)
                .build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with("")
                .noargs();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

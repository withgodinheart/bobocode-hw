package com.bobocode.bobo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

public class PublishNameInRabbit {

    @SneakyThrows
    public static void main(String[] args) {
        var json = new ObjectMapper().writeValueAsString(new Name("Oleksandr", "Vashchenko"));

        var factory = new ConnectionFactory();
        factory.setHost("93.175.204.87");
        factory.setPort(5672);

        try (var connection = factory.newConnection();
             var channel = connection.createChannel()) {
            channel.basicPublish("participants-direct-exchange", "", null, json.getBytes(StandardCharsets.UTF_8));
        }
    }

    record Name(String firstName, String lastName) {
    }
}

package com.bobocode.hw39;

import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

public class PublishRabbit {

    @SneakyThrows
    public static void main(String[] args) {
        var connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("93.175.204.87");

        try (var connection = connectionFactory.newConnection();
             var channel = connection.createChannel()) {
            channel.basicPublish("", "participants-queue", null, "Oleksandr Vashchenko".getBytes(StandardCharsets.UTF_8));
        }
    }
}

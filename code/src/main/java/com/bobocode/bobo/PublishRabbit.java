package com.bobocode.bobo;

import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

public class PublishRabbit {

    @SneakyThrows
    public static void main(String[] args) {
        var connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (var connection = connectionFactory.newConnection();
             var channel = connection.createChannel()) {
            channel.basicPublish("", "participants-queue", null, "Oleksandr Vashchenko".getBytes(StandardCharsets.UTF_8));
        }
    }
}

package com.bobocode.rabbitudemy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListenerService {

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "bobocode-queue")
    @SneakyThrows
    private void consume(String message) {
        var msg = objectMapper.readValue(message, Msg.class);
        System.out.println("---> consumed: " + msg);
    }

    @RabbitListener(queues = "bobocode-queue")
    private void consume(Msg message) {
        System.out.println("---> consumed: " + message);
    }
}

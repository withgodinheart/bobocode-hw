package com.bobocode.rabbitudemy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublishService implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        var i = 100;
        while (i > 0) {
            produce(prepareJson());
            i--;
            Thread.sleep(3000);
        }
    }

    @SneakyThrows
    private String prepareJson() {
        var msg = new Msg("spring", RandomStringUtils.randomAlphabetic(5));
        return objectMapper.writeValueAsString(msg);
    }

    private Msg prepareMsg() {
        return new Msg("spring", RandomStringUtils.randomAlphabetic(5));
    }

    private void produce(Object msg) {
        System.out.println("<--- produced: " + msg);
        rabbitTemplate.convertAndSend("bobocode-exchange", "udemy.test", msg);
    }
}

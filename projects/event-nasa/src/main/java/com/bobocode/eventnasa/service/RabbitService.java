package com.bobocode.eventnasa.service;

import com.bobocode.eventnasa.dto.Picture;
import com.bobocode.eventnasa.dto.Request;
import com.bobocode.eventnasa.dto.Result;
import com.bobocode.eventnasa.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.bobocode.eventnasa.config.RabbitConfig.QUEUE_NAME;
import static com.bobocode.eventnasa.config.RabbitConfig.RESULT_QUEUE_NAME;

@Service
@RequiredArgsConstructor
public class RabbitService {

    private final RabbitTemplate rabbitTemplate;
    private final NasaService nasaService;

    @RabbitListener(queues = QUEUE_NAME)
    public void listen(Request request) {
        var url = nasaService.findPicture(request);
        var result = createResult(request, url);
        publish(result);
    }

    @SneakyThrows
    public void publish(Result result) {
        rabbitTemplate.convertAndSend("", RESULT_QUEUE_NAME, result);
    }

    private Result createResult(Request request, String url) {
        return new Result(new User("Alex", "Vashchenko"), request, new Picture(url));
    }
}

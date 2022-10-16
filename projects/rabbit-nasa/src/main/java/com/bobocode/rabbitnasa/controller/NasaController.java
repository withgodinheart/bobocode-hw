package com.bobocode.rabbitnasa.controller;

import com.bobocode.rabbitnasa.dto.CommandDto;
import com.bobocode.rabbitnasa.dto.RequestDto;
import com.bobocode.rabbitnasa.dto.ResponseDto;
import com.bobocode.rabbitnasa.exception.InvalidCommandException;
import com.bobocode.rabbitnasa.service.NasaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.NoSuchElementException;

import static com.bobocode.rabbitnasa.config.RabbitConfig.EXCHANGE_NAME;

@RestController
@RequestMapping("/mars/pictures")
@RequiredArgsConstructor
@Slf4j
public class NasaController {

    private final NasaService nasaService;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping(value = "/largest", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> generateId(@RequestBody RequestDto request) {
        var id = nasaService.generateId(request);
        var command = new CommandDto(id, request);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,
                "", command);
        var url = ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment(id).build().toUriString();

        return ResponseEntity.ok(new ResponseDto("success", url));
    }

    @GetMapping(value = "/largest/{commandId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getPicture(@PathVariable String commandId) {
        return ResponseEntity.ok(nasaService.getPicture(commandId));
    }

    @ExceptionHandler({InvalidCommandException.class, NoSuchElementException.class})
    private ResponseEntity<?> handle(Exception e) {
        log.warn(e.getMessage());
        return ResponseEntity.ok(new ResponseDto("failed", "No pictures found"));
    }
}

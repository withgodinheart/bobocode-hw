package com.bobocode.hw21.controller;

import com.bobocode.hw21.model.dto.RequestDto;
import com.bobocode.hw21.service.PictureService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/steal/pictures/")
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @PostMapping
    @SneakyThrows
    public void doWork(@RequestBody RequestDto requestDto) {
        log.info("SOL: {}", requestDto.getSol());
        log.info("RESPONSE: {}", pictureService.run(requestDto.getSol()));
    }
}

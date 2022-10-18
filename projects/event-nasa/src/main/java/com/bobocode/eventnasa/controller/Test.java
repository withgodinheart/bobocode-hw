package com.bobocode.eventnasa.controller;

import com.bobocode.eventnasa.dto.Picture;
import com.bobocode.eventnasa.dto.Request;
import com.bobocode.eventnasa.dto.Result;
import com.bobocode.eventnasa.dto.User;
import com.bobocode.eventnasa.service.NasaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class Test {

    private final NasaService nasaService;
    private final ObjectMapper objectMapper;

    @GetMapping("/test")
    @SneakyThrows
    public void test() {
        var req = new Request(15, "NAVCAM");
        var url = nasaService.findPicture(new Request(15, "NAVCAM"));
        var obj = new Result(new User("Alex", "Vashchenko"), req, new Picture(url));
        var json = objectMapper.writeValueAsString(obj);
        System.out.println(json);
    }
}

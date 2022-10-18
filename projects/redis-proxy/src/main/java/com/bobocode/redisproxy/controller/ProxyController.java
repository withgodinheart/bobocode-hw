package com.bobocode.redisproxy.controller;

import com.bobocode.redisproxy.service.ProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mars/pictures/largest")
@RequiredArgsConstructor
public class ProxyController {

    private final ProxyService proxyService;

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] get(@RequestParam Integer sol, @RequestParam(required = false) String camera) {
        return proxyService.proxy(sol, camera);
    }
}

package com.bobocode.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class MainController {

    @GetMapping("/authenticated")
    public String authenticated() {
        return "Only for authenticated users";
    }
}

package com.bobocode.boot;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servlet/attributes")
@RequiredArgsConstructor
public class ExposeServletContext {

    private final ApplicationContext ctx;

    @GetMapping()
    public String[] getAttributes() {
        return ctx.getBeanDefinitionNames();
    }
}

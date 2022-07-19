package com.bobocode.boot;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/servlet")
@RequiredArgsConstructor
public class ExposeServletContext {

    /*
    ONLINE TRAINING 15 | TASK 4
:point_right: Expose the context via the endpoint
Create a simple Spring  web app
Build the endpoint /servlet/attributes/ the returns a list of all Servlet Context attribute names
Build the endpoint /servlet/attributes/{name} the returns a String representation of a specific servlet attribute by its name
Build the endpoint /spring/beans that returns a list of all bean names in the Spring Context
Build the endpoint /spring/beans/{name} that returns a String representation of a specific bean by its name
     */

    private final ServletContext ctx;
    private final ApplicationContext appCtx;

    @GetMapping("/attributes")
    public List<String> getAttributes() {
        final List<String> list = new ArrayList<>();
        ctx.getAttributeNames().asIterator().forEachRemaining(list::add);

        return list;
    }

    @GetMapping("/attributes/{name}")
    public String getAttributeByName(@PathVariable final String name) {
        return ctx.getAttribute(name).toString();
    }

    @GetMapping("/beans")
    public String[] getBeans() {
        return appCtx.getBeanDefinitionNames();
    }

    @GetMapping("/beans/{name}")
    public String getBeanByName(@PathVariable final String name) {
        return appCtx.getBean(name).toString();
    }
}

package com.bobocode.app;

import com.bobocode.app.service.EchoService;
import com.bobocode.trimmer.annotation.EnableStringTrimming;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@EnableStringTrimming
@Component
public class Main {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext("com.bobocode.app");
        var bean = ctx.getBean(EchoService.class);
        var input = "   Petrovich  ";
        System.out.println(bean.echo(input));

    }
}

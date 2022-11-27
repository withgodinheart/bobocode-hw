package com.bobocode.bobo.enableconfig.app;

import com.bobocode.bobo.enableconfig.annotation.EnableDateLog;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@EnableDateLog
@Configuration
public class App {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext("com.bobocode.my.enableconfig.app");
        var bean = ctx.getBean(DatedService.class);
        bean.print();
    }
}

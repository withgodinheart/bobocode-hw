package com.bobocode.my.enableconfig.app;

import com.bobocode.my.enableconfig.annotation.Dated;
import org.springframework.stereotype.Component;

@Dated
@Component
public class DatedService {

    public void print() {
        System.out.println("Hello from " + this.getClass().getSimpleName());
    }
}

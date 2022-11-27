package com.bobocode.bobo.enableconfig.app;

import com.bobocode.bobo.enableconfig.annotation.Dated;
import org.springframework.stereotype.Component;

@Dated
@Component
public class DatedService {

    public void print() {
        System.out.println("Hello from " + this.getClass().getSimpleName());
    }
}

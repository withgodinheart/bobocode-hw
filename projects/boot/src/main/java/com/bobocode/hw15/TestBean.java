package com.bobocode.hw15;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component()
public final class TestBean implements Test {

    @Autowired
    TestBean1 obj;
}

package com.bobocode.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component()
public final class TestBean implements Test {

    @Autowired
    TestBean1 obj;
}

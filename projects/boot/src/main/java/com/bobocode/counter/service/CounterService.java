package com.bobocode.counter.service;

import org.springframework.stereotype.Service;

@Service
public class CounterService {

    int i = 0;

    public int increment() {
        return ++i;
    }
}

package com.bobocode.hw15.yv.service;

import com.bobocode.hw15.yv.context.Bean;

@Bean("morning")
public class MorningService implements DayService {
    public void greeting() {
        System.out.println("Good morning everybody!");
    }
}

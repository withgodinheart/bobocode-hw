package com.bobocode.lesson15.hw15.service;

import com.bobocode.lesson15.hw15.context.Bean;

@Bean("morning")
public class MorningService implements DayService {
    public void greeting() {
        System.out.println("Good morning everybody!");
    }
}

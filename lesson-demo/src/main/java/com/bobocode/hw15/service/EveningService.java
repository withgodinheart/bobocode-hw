package com.bobocode.lesson15.hw15.service;

import com.bobocode.lesson15.hw15.context.Bean;

@Bean("evening")
public class EveningService implements DayService {
    public void greeting() {
        System.out.println("Good evening everybody!");
    }
}
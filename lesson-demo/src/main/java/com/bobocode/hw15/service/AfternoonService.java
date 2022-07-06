package com.bobocode.hw15.service;

import com.bobocode.lesson15.hw15.context.Bean;

@Bean
public class AfternoonService implements DayService {
    public void greeting() {
        System.out.println("Good afternoon everybody!");
    }
}

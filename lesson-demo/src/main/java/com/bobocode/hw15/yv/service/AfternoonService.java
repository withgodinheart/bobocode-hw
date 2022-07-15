package com.bobocode.hw15.yv.service;

import com.bobocode.hw15.yv.context.Bean;

@Bean
public class AfternoonService implements DayService {
    public void greeting() {
        System.out.println("Good afternoon everybody!");
    }
}

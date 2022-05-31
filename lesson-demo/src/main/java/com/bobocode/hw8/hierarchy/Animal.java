package com.bobocode.hw8.hierarchy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Animal {

    String weight;

    @Override
    public String toString() {
        return "Animal{" +
                "weight='" + weight + '\'' +
                '}';
    }
}

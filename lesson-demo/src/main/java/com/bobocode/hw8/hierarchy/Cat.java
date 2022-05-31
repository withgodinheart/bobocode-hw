package com.bobocode.hw8.hierarchy;

public class Cat extends Animal {

    public Cat(String weight) {
        super(weight);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "weight='" + weight + '\'' +
                '}';
    }
}

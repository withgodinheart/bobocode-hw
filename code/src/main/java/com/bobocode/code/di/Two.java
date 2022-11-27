package com.bobocode.code.di;

public interface Two {

    default void two() {
        System.out.println(Two.class.getSimpleName());
    }

    default void print() {
        System.out.println("Print: " + Two.class.getSimpleName());
    }

    void test();
}

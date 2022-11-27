package com.bobocode.code.di;

public interface One {

    default void one() {
        System.out.println(One.class.getSimpleName());
    }

//    default void print() {
//        System.out.println("Print: " + One.class.getSimpleName());
//    }

    void test();
}

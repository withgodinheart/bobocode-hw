package com.bobocode.hw7;

import java.util.function.Consumer;

public class CheckLambdaBytecode {

    public static void main(String[] args) {

        Consumer<Integer> cs = el -> System.out.println(el);
        Consumer<Integer> cs1 = System.out::println;
        Consumer<Integer> cs2 = new Consumer<>() {
            @Override
            public void accept(Integer o) {
                System.out.println(o);
            }
        };

        for (var m : CheckLambdaBytecode.class.getDeclaredMethods()) {
            System.out.println(m.getName());
        }

    }
}

package com.bobocode.hw8;

import java.util.stream.IntStream;

public class Prime {

    public static void main(String[] args) {
        primeNumbers();
    }

    private static void primeNumbers() {
        int sum = IntStream.iterate(2, x -> x + 1)
                .filter(x -> IntStream.range(2, x).noneMatch(i -> x % i == 0))
                .skip(500)
                .limit(20)
                .sum();

        System.out.println(sum);
    }
}

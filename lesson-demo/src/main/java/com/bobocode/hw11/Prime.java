package com.bobocode.hw11;

import java.util.stream.IntStream;

public class Prime {

    public static void main(String[] args) {
        System.out.println(prime(500, 20));
        System.out.println(sequence(-4, -29, 5, 2, 3, -9, 12));
    }


    private static int sequence(final int... s) {
        return IntStream.of(s)
                .dropWhile(x -> Math.abs(x) != x)
                .takeWhile(x -> Math.abs(x) == x)
                .sum();
    }

    private static int prime(final int start, final int limit) {
        return IntStream.iterate(2, x -> x + 1)
                .filter(x -> IntStream.range(2, x).noneMatch(i -> x % i == 0))
                .skip(start)
                .limit(limit)
                .sum();
    }
}

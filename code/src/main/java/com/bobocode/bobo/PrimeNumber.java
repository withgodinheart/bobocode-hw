package com.bobocode.bobo;

import java.util.Arrays;
import java.util.stream.IntStream;

public class PrimeNumber {

    public static void main(String[] args) {

        var ints = IntStream.iterate(2, x -> x + 1)
                .filter(x -> IntStream.range(2, x).noneMatch(i -> x % i == 0))
                .limit(100)
                .toArray();

        System.out.println(Arrays.toString(ints));
    }
}

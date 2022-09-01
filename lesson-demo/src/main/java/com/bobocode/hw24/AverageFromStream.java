package com.bobocode.hw24;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class AverageFromStream {

    public static void main(String[] args) {

        var arr = new int[]{1, 2, 3, 6, 7, 8, 3, 4, 5};
        var counter = new AtomicInteger(0);
        int sum = Arrays.stream(arr)
                .peek(el -> counter.incrementAndGet())
                .sum();
        System.out.println((float) sum / counter.get());

        var counter1 = new AtomicInteger(1);
        Arrays.stream(arr)
                .reduce((a, b) -> {
                    counter1.incrementAndGet();
                    return Integer.sum(a, b);
                })
                .ifPresent(num -> System.out.println((float) num / counter1.get()));
    }
}

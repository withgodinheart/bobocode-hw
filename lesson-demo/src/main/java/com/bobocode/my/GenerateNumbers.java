package com.bobocode.my;

import java.util.stream.IntStream;

public class GenerateNumbers {

    public static void main(String[] args) {
        var integers = IntStream.generate(() -> 1)
                .limit(1000)
                .boxed()
                .toList();
        System.out.println(integers);
    }
}

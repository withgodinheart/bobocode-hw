package com.bobocode.my;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InterviewCase {

    public static void main(String[] args) {
        countElements();
        System.out.println(fncNMinus2(10));
        System.out.println(fncNMinus2Recursively(10));
    }

    private static int fncNMinus2(int n) {
        int sum = 0;
        for (int i = n; i > 0; i -= 2) {
            sum += i;
        }

        return sum;
    }

    private static int fncNMinus2Recursively(int n) {
        if (n < 0) return 0;
        return n + fncNMinus2Recursively(n - 2);
    }


    private static void countElements() {
        var list = List.of("Bob", "Vasya", "Kolya", "Bob", "Sasha");

        System.out.println(list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
        System.out.println(list.stream().collect(Collectors.groupingBy(k -> k, Collectors.mapping(k -> k, Collectors.counting()))));
        System.out.println(list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.toList())));
    }
}

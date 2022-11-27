package com.bobocode.bobo;

import java.util.Arrays;
import java.util.List;

public class ReduceWithCombiner {

    public static void main(String[] args) {
        var users = Arrays.asList(new User("John", 30), new User("Julie", 35));

        /**
         * Doesn't work without combiner because 1st and 2nd params has different types
         */
        // users.parallelStream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.age());

        /**
         * Works with combiner, because under the hood the implementation of reduce
         * consumes BiFunction with 1st param type = return type
         */
        System.out.println(users.parallelStream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.age(), Integer::sum));
    }

    record User(String name, int age) {
    }

    public static void test() {
        var sdf = List.of("1", "sdf", "23r", "45", "34");
        var result = "";
        var reduce = sdf.stream().reduce(result, (str, el) -> str += el);
        System.out.println(reduce);
    }
}

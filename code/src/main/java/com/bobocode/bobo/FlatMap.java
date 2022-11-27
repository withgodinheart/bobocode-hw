package com.bobocode.bobo;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class FlatMap {

    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test1() {
        var stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8).map(Stream::of);
//        stream.map(el -> el).toList().forEach(System.out::println);
        stream.flatMap(el -> el).toList().forEach(System.out::println);
    }

    public static void test2() {
        var humans = asList(
                new Human("Sam", asList("Buddy", "Lucy")),
                new Human("Bob", asList("Frankie", "Rosie")),
                new Human("Marta", asList("Simba", "Tilly")));

        humans.stream()
                .flatMap(human -> human.pets.stream())
                .toList()
                .forEach(System.out::println);
    }

    record Human(String name, List<String> pets) {
    }
}

package com.bobocode.my;

import java.util.stream.Stream;

public class FlatMap {

    public static void main(String[] args) {

        var stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8).map(Stream::of);
        var map = stream.map(el -> el).toList();
        var flatMap = stream.flatMap(el -> el).toList();
    }
}

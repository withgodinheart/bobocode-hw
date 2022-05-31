package com.bobocode.hw7;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Lambda {

    public static void main(String[] args) {

        final BiFunction<String, Integer, String> lambda = (name, amt) -> name + " get mark: " + amt;
        System.out.println(lambda.apply("Ivan", 5));

        final BiFunction<String, Integer, String> reference = Lambda::reference;
        System.out.println(reference.apply("Petro", 4));


        Function<String, String> identity = Function.identity();
        String ivan = identity.apply("IVAN");
        System.out.println(ivan);
    }

    private static String reference(final String name, final Integer amt) {
        return name + " get mark: " + amt;
    }
}

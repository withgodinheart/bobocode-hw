package com.bobocode.hw26;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

public class ReflectionFieldTest {

    public static void main(String[] args) {

        Field[] fields = Arrays.stream(String.class.getDeclaredFields())
                .sorted(Comparator.comparing(Field::getName))
                .toArray(Field[]::new);
    }
}

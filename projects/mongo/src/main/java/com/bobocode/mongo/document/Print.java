package com.bobocode.mongo.document;

import java.util.Arrays;

public enum Print {
    INSTANCE;

    public void print() {
        System.out.println("WORKING...");
        System.out.println(Arrays.toString(Print.values()));
    }
}

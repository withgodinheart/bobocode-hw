package com.bobocode.hw24;

public class ArgumentsByReferenceOrByValue {

    public static void main(String[] args) {
        var obj = new Data();
        method1(obj);
        System.out.println(obj.status);

        var el = 7;
        method(el);
        System.out.println(el);
    }

    private static void method(Integer el) {
        el = 1;
    }

    private static void method1(Data obj) {
        obj.status = true;
    }

    private static class Data {
        boolean status = false;
    }
}

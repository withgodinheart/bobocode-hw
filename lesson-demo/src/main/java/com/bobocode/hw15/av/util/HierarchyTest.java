package com.bobocode.hw15.av.util;

import lombok.ToString;

import java.util.HashMap;

public class HierarchyTest {

    public static void main(String[] args) {
        var beanType = Parent.class;

        Another anotherChild = new AnotherChild();

        var map = new HashMap<String, Object>();
        map.put("child", new Child());
        map.put("grandChild", new GrandChild());
        map.put("anotherChild", anotherChild);

        map.forEach((k, v) -> {
            System.out.println("-------> " + k);
            System.out.println("isAssignableTo " + beanType.getSimpleName() + ": " + beanType.isAssignableFrom(v.getClass()));
            System.out.println("isInstance of " + beanType.getSimpleName() + ": " + beanType.isInstance(v));
            System.out.println(beanType.cast(v));
        });
    }

    @ToString
    static class Child implements Parent {
        public int childField = 0;
    }

    @ToString
    static class GrandChild extends Child {
        public int grandChildField = 0;
    }

    @ToString
    static class AnotherChild implements Parent, Another {
        public int anotherChildField = 0;
    }

    static interface Parent {
    }

    static interface Another {
    }
}

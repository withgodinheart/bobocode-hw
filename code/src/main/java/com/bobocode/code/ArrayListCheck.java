package com.bobocode.code;

import java.util.ArrayList;

public class ArrayListCheck {

    public static void main(String[] args) {
        var list = new ArrayList<String>();
//        list.add(1, "new");
        list.add(0, "new");

        System.out.println(list);

        int res = (int) (10 * 1.5f);
        System.out.println(res);

    }
}

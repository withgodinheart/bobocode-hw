package com.bobocode.code;

import java.util.Arrays;

public class SystemArrayCopy {

    public static void main(String[] args) {

        String str = null;
        var item = (Object) str;

        var data = new int[]{0, 1, 2, 3, 4, 5};
        var index = 1;
        var size = data.length;
        System.arraycopy(data, index + 1, data, index, size - index - 1);

        System.out.println(Arrays.toString(data));
    }
}

package com.bobocode.bobo;

import java.util.Base64;

public class Encoder {

    public static void main(String[] args) {
        var message = "String ti be encoded";
        var encodedMessage = Base64.getEncoder().encodeToString(message.getBytes());
        System.out.println(encodedMessage);
    }
}

package com.bobocode.bobo;

import lombok.SneakyThrows;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class GetSocket {


    @SneakyThrows
    public static void main(String[] args) {
        var host = "75.2.76.189";

        try (var socket = new java.net.Socket(host, 443)) {
            var writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream())); // or new OutputStreamWriter
            writer.println("GET " + host + "/ru/bets HTTP/1.1");
            writer.println("Host:" + host);
            writer.println();
            writer.flush();

            var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            reader.lines().forEach(System.out::println);
        }
    }
}

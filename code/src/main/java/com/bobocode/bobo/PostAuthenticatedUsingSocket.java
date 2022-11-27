package com.bobocode.bobo;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class PostAuthenticatedUsingSocket {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    @SneakyThrows
    public static void main(String[] args) {
        var socket = new Socket(HOST, PORT);
        var writer = getWriter(socket);
        var body = "username=ju22user&password=ju22pass";
//        var secret = Base64.getEncoder().encodeToString("ju22user:ju22pass".getBytes());

        writer.println("POST " + HOST + "/login HTTP/1.1");
        writer.println("Host:" + HOST);
//        writer.println("Authorization: Basic " + secret);
        writer.println("Content-Type: application/x-www-form-urlencoded");
        writer.println("Content-Length: " + body.length());
        writer.println();
        writer.println(body);
        writer.println();
        writer.flush();

        var reader = getReader(socket);
        reader.lines().forEach(System.out::println);
    }

    @SneakyThrows
    private static PrintWriter getWriter(Socket socket) {
        return new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @SneakyThrows
    private static BufferedReader getReader(Socket socket) {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}

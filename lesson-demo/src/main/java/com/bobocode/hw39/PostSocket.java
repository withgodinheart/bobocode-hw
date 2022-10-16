package com.bobocode.hw39;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;

public class PostSocket {


    @SneakyThrows
    public static void main(String[] args) {
        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(new Name("Oleksandr", "Vashchenko"));
        var host = "93.175.204.87";

        try (var socket = new java.net.Socket("93.175.204.87", 8080)) {
            var writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream())); // or new OutputStreamWriter
            writer.println("POST " + host + "/participants HTTP/1.1");
            writer.println("Host:" + host);
            writer.println("Content-Type: application/json");
            writer.println("Content-Length: " + json.getBytes().length);
            writer.println();
            writer.println(json);
            writer.println();
            writer.flush();
        }
    }

    record Name(String firsName, String lastName) {

    }
}

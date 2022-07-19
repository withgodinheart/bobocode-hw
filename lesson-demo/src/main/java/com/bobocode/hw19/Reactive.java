package com.bobocode.hw19;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Reactive {

    @SneakyThrows
    public static void main(String[] args) {

        var json = "{\n" +
                "  \"firstName\": \"Alex\",\n" +
                "  \"lastName\": \"Vashchenko\",\n" +
                "  \"reactiveProgrammingLevel\": 3,\n" +
                "  \"hasSpringWebFluxExperience\": false\n" +
                "}";

        try (Socket clientSocket = new Socket("93.175.204.87", 8080);
             OutputStream outputStream = clientSocket.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream))) {

            writer.println("POST /reactive/persons HTTP/1.1");
            writer.println("Host: 93.175.204.87");
            writer.println("Content-Type: application/json");
            writer.println("Content-Length: " + json.length());
            writer.println();
            writer.println(json);
            writer.flush();

            try (InputStream inputStream = clientSocket.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                reader.lines().forEach(System.out::println);
            }

        } catch (Exception e) {
            throw new RuntimeException("Socket error", e);
        }
    }
}

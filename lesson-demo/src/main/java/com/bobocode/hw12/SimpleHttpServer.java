package com.bobocode.hw12;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SimpleHttpServer {

    private static final Integer PORT = 8080;
    private static final String METHOD = "GET";
    private static final String ENDPOINT = "/hello";
    private static final String HOST = "localhost";
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CONTENT_TYPE = "Content-Type: application/json";
    private static final ExecutorService EXECUTORS = Executors.newFixedThreadPool(50);

    public static void main(String[] args) {
        startServer();
    }

    private static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                EXECUTORS.submit(() -> processRequest(clientSocket));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to start server", e);
        }

    }

    private static void processRequest(Socket socket) {
        try (InputStream inputStream = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String httpMetadata = reader.readLine();
            String hostMetadata = reader.readLine();
            List<String> errors = validateHttpMetadata(httpMetadata, hostMetadata);

            if (!errors.isEmpty()) {
                sendResponse(socket, ResponseCode.ERROR, () -> {
                    return errors.stream().collect(Collectors.toMap(e -> "error_" + (errors.indexOf(e) + 1), Function.identity()));
                });
            } else {
                sendResponse(socket, ResponseCode.SUCCESS, () -> retrieveQueryParams(httpMetadata));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed while processing request", e);
        }
    }

    private static List<String> validateHttpMetadata(String httpMetadata, String hostMetadata) {

        List<String> httpValidationErrors = new ArrayList<>();

        List<String> httpMetadataParts = Arrays.asList(httpMetadata.split(" "));
        List<String> hostMetadataParts = Arrays.stream(hostMetadata.split(":")).map(String::trim).toList();

        if (httpMetadataParts.size() < 3) {
            httpValidationErrors.add("Bad http syntax");
        }

        if (!httpMetadataParts.get(0).equals(METHOD)) {
            httpValidationErrors.add("Unsupported http method");
        }

        if (!httpMetadataParts.get(1).contains(ENDPOINT)) {
            httpValidationErrors.add("Unsupported http endpoint");
        }

        if (!httpMetadataParts.get(2).equals(HTTP_VERSION)) {
            httpValidationErrors.add("Unsupported http version");
        }

        if (hostMetadataParts.size() != 2 ||
                !hostMetadataParts.get(0).equals("Host") ||
                !hostMetadataParts.get(1).equals(HOST)) {
            httpValidationErrors.add("Unsupported host header");
        }

        return httpValidationErrors;
    }

    private static Map<String, String> retrieveQueryParams(String httpMetadata) {
        String endpointQueryParams = httpMetadata.split(" ")[1];
        String queryParams = endpointQueryParams.split("\\?")[1];
        String[] pairs = queryParams.split("&");

        return Arrays.stream(pairs).map(pair -> {
            String[] keyValue = pair.split("=");
            return Map.entry(keyValue[0], keyValue[1]);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static void sendResponse(Socket socket, ResponseCode code, Supplier<Map<String, String>> supplier) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));

        Map<String, String> jsonMessages = supplier.get();

        String json = new ObjectMapper().writeValueAsString(jsonMessages);
        writer.println(HTTP_VERSION + " " + code.codeValue);
        writer.println(CONTENT_TYPE);
        writer.println("Date: " + ZonedDateTime.now().toString());
        writer.println("Content-Length: " + json.length());
        writer.println("");
        writer.println(json);
        writer.flush();
    }

    private enum ResponseCode {
        SUCCESS("200 OK"),
        ERROR("400 Bad Request");

        private final String codeValue;

        ResponseCode(String codeValue) {
            this.codeValue = codeValue;
        }
    }
}

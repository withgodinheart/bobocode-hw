package com.bobocode.hw14;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class SecureSocketConnection {
    private static final String URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=16&api_key=DEMO_KEY";
    private static final Integer HTTP_PORT = 80;
    private static final Integer HTTPS_PORT = 443;

    enum ConnectionType {
        HTTP {
            @Override
            Socket buildSocket(String url) throws IOException {
                return new Socket(new URL(url).getHost(), HTTP_PORT);
            }
        },
        HTTPS {
            @Override
            Socket buildSocket(String url) throws IOException {
                var socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(new URL(url).getHost(), HTTPS_PORT);
                socket.startHandshake();
                return socket;
            }
        };

        abstract Socket buildSocket(String host) throws IOException;
    }

    public static void main(String[] args) {
        getMaxImage(getImagesUrls());
    }

    private static void getMaxImage(List<String> urls) {
        urls.stream()
                .map(SecureSocketConnection::getRedirectLink)
                .filter(url -> !url.isEmpty())
                .map(url ->
                {
                    var photo = new Photo();
                    photo.setImgSrc(url);
                    photo.setSize(getImageSize(url));
                    return photo;
                })
                .max(Comparator.comparing(Photo::getSize))
                .ifPresent(System.out::println);
    }

    private static List<String> getImagesUrls() {
        try (var socket = ConnectionType.HTTPS.buildSocket(URL);
             var writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
             var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            sendRequest(writer, HttpMethod.GET, URL);
            return extractImagesUrls(reader);
        } catch (Exception e) {
            throw new RuntimeException("Error while using socket connection", e);
        }
    }

    private static String getRedirectLink(String url) {
        try (var socket = ConnectionType.HTTP.buildSocket(url);
             var writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
             var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            sendRequest(writer, HttpMethod.GET, url);
            BiFunction<BufferedReader, String, String> getRedirectLink = SecureSocketConnection::getHeaderValue;
            return getRedirectLink.apply(reader, "Location");
        } catch (Exception e) {
            throw new RuntimeException("Error while using socket connection", e);
        }
    }

    private static Long getImageSize(String url) {
        try (var socket = ConnectionType.HTTPS.buildSocket(url);
             var writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
             var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            sendRequest(writer, HttpMethod.HEAD, url);
            BiFunction<BufferedReader, String, String> getImageSize = SecureSocketConnection::getHeaderValue;
            var value = getImageSize.apply(reader, "Content-Length");
            return !value.isEmpty() ? Long.parseLong(value) : 0L;
        } catch (Exception e) {
            throw new RuntimeException("Error while using socket connection", e);
        }
    }

    private static List<String> extractImagesUrls(BufferedReader reader) throws JsonProcessingException {
        var json = reader.lines()
                .dropWhile(line -> !line.isBlank())
                .filter(line -> line.contains("{") || line.contains("}"))
                .collect(Collectors.joining());

        return new ObjectMapper().readTree(json).get("photos").findValuesAsText("img_src");
    }

    private static void sendRequest(PrintWriter writer, HttpMethod method, String url) throws MalformedURLException {
        var urlObj = new URL(url);
        var query = Objects.nonNull(urlObj.getQuery()) ? ("?" + urlObj.getQuery()) : "";
        writer.println(method + " " + urlObj.getPath() + query + " HTTP/1.1");
        writer.println("Host: " + urlObj.getHost());
        writer.println("Connection: close");
        writer.println();
        writer.flush();
    }

    private static String getHeaderValue(BufferedReader reader, String header) {
        return reader.lines()
                .filter(line -> line.contains(header))
                .findFirst()
                .map(line -> line.split(" "))
                .map(arr -> arr[1])
                .orElse("");
    }
}

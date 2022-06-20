package com.bobocode.hw13;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class CustomSession {

    private final static String CUSTOM_SESSION_COOKIE_NAME = "CUSTOM_SESS_ID";
    private static Map<String, Map<String, String>> sessionMap = new ConcurrentHashMap<>();

    public static Optional<String> getByKey(HttpServletRequest req, String key) {
        var allData = getAll(req);
        return Optional.ofNullable(allData.get(key));
    }

    public static Map<String, String> getAll(HttpServletRequest req) {
        var id = getId(req);
        return Objects.nonNull(id)
                ? sessionMap.getOrDefault(id, Collections.emptyMap())
                : Collections.emptyMap();
    }

    public static void setData(HttpServletRequest req, HttpServletResponse resp, Map<String, String> data) {
        var id = getId(req);
        var newId = Objects.nonNull(id) ? id : generateId();
        sessionMap.put(newId, data);
        setId(resp, newId);
    }

    public static void remove(HttpServletRequest req) {
        var id = getId(req);
        sessionMap.remove(id);
    }

    private static String getId(HttpServletRequest req) {
        var cookies = req.getCookies();

        if (Objects.nonNull(cookies)) {
            return Stream.of(cookies)
                    .filter(cookie -> cookie.getName().equals(CUSTOM_SESSION_COOKIE_NAME))
                    .findFirst()
                    .map(Cookie::getValue)
                    .get();
        }

        return null;
    }

    private static void setId(HttpServletResponse resp, String sessionId) {
        resp.addCookie(new Cookie(CUSTOM_SESSION_COOKIE_NAME, sessionId));
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }
}

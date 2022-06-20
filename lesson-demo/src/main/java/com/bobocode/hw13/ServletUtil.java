package com.bobocode.hw13;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ServletUtil {

    public static Map<String, String> getParams(HttpServletRequest req) {

        Map<String, String> parametersMap = new HashMap<>();
        final Enumeration<String> parameterNames = req.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            final String name = parameterNames.nextElement();
            final String value = req.getParameterValues(name)[0];
            parametersMap.put(name, value);
        }
        return parametersMap;
    }
}

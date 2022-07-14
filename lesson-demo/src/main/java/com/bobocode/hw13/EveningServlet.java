package com.bobocode.hw13;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Optional;

@WebServlet("/evening")
public class EveningServlet extends HttpServlet {

    private final static String REQUIRED_FIELD_NAME = "name";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var defaultSession = Optional.ofNullable(req.getParameter("default"));
        if (defaultSession.isPresent()) {
            withDefaultSession(req, resp);
        } else {
            withCustomSession(req, resp);
        }
    }

    private void withDefaultSession(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var queryParamName = Optional.ofNullable(req.getParameter(REQUIRED_FIELD_NAME));
        var session = req.getSession();
        var sessionName = Optional.ofNullable((String) session.getAttribute(REQUIRED_FIELD_NAME));

        if (sessionName.isEmpty() && queryParamName.isPresent()) {
            session.setAttribute(REQUIRED_FIELD_NAME, queryParamName.get());
        }

        var responseName = queryParamName
                .or(() -> sessionName)
                .orElse("Buddy");

        final PrintWriter writer = resp.getWriter();
        writer.println("Good evening, " + responseName);
    }

    private void withCustomSession(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Remove session if provided 'Remove' parameter
        var remove = Optional.ofNullable(req.getParameter("remove"));
        if (remove.isPresent()) {
            CustomSession.remove(req);
        }

        var queryParamName = Optional.ofNullable(req.getParameter(REQUIRED_FIELD_NAME));
        var sessionName = CustomSession.getByKey(req, REQUIRED_FIELD_NAME);

        if (sessionName.isEmpty() && queryParamName.isPresent()) {
            CustomSession.setData(
                    req,
                    resp,
                    Collections.singletonMap(REQUIRED_FIELD_NAME, queryParamName.get())
            );
        }

        var responseName = queryParamName
                .or(() -> sessionName)
                .orElse("Buddy");

        final PrintWriter writer = resp.getWriter();
        writer.println("Good evening, " + responseName);
    }
}

package ru.job4j.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cinema.model.dto.session.SessionRequest;
import ru.job4j.cinema.model.dto.session.SessionResponse;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionServlet extends HttpServlet {
    private final SessionService sessionService = SessionService.instOf();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        SessionRequest sessionRequest = objectMapper.readValue(req.getReader()
                .lines()
                .reduce("", (accumulator, actual) -> accumulator + actual), SessionRequest.class);

        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(objectMapper.writeValueAsString(SessionResponse.builder()
                .sessionState(sessionService.getSession(sessionRequest.getId()))
                .build()));
        writer.flush();
        writer.close();
    }
}

package ru.job4j.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import ru.job4j.cinema.model.dto.BookingRequest;
import ru.job4j.cinema.model.dto.BookingResponse;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.PlaceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HallServlet extends HttpServlet {
    private final HallService hallService = HallService.instOf();
    private final PlaceService placeService = PlaceService.instOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String response = null;
        ObjectMapper objectMapper = new ObjectMapper();

        val defaultHall = hallService.getLastHall();

        if (defaultHall.isPresent()) {
            response = objectMapper.writeValueAsString(defaultHall.get());
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        resp.getWriter().write(response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        BookingRequest bookingRequest = objectMapper.readValue(req.getReader()
                        .lines()
                        .reduce("", (accumulator, actual) -> accumulator + actual),
                BookingRequest.class);

        placeService.proceedPlace(bookingRequest);

        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(objectMapper.writeValueAsString(BookingResponse.builder().status(200).message("Success").build()));
        writer.flush();
        writer.close();
    }
}

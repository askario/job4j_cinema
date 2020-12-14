package ru.job4j.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cinema.model.dto.booking.BookingRequest;
import ru.job4j.cinema.model.dto.booking.BookingResponse;
import ru.job4j.cinema.service.SeatService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SeatServlet extends HttpServlet {
    private final SeatService seatService = SeatService.instOf();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        BookingRequest bookingRequest = objectMapper.readValue(req.getReader()
                        .lines()
                        .reduce("", (accumulator, actual) -> accumulator + actual),
                BookingRequest.class);

        seatService.proceed(bookingRequest);

        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(objectMapper.writeValueAsString(BookingResponse.builder().status(200).message("Success").build()));
        writer.flush();
        writer.close();
    }
}

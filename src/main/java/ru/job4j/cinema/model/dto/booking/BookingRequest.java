package ru.job4j.cinema.model.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.job4j.cinema.model.Account;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private int session;
    private int hall;
    private int id;
    private int row;
    private int place;
    private int price;
    private Account account;
}

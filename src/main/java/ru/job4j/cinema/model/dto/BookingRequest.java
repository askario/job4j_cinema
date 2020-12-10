package ru.job4j.cinema.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.job4j.cinema.model.Account;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private int id;
    private int row;
    private int position;
    private int cost;
    private Account account;
}

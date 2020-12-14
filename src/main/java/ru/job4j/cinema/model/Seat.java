package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Seat {
    private int id;
    private int row;
    private int place;
    private int hallId;
}

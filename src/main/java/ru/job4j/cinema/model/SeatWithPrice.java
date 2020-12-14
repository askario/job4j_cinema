package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SeatWithPrice {
    private int sessionId;
    private int id;
    private int row;
    private int place;
    private int hallId;
    private boolean isOccupied;
    private int price;
}

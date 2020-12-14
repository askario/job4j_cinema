package ru.job4j.cinema.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class Order {
    private int id;
    private int sessionId;
    private int accountId;
    private int hallId;
    private int seatId;
    private int price;
}

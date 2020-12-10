package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Place {
    private int id;
    private int row;
    private int position;
    private boolean isOccupied;
    private int hall_id;
    private int cost;
    private int account_id;
}

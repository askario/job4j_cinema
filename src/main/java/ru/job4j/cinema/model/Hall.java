package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Hall {
    private int id;
    private String name;
    private List<List<Place>> places;
}
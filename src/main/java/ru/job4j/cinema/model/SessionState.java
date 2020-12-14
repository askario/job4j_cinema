package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionState {
    private Session session;
    private Hall hall;
    private Map<Integer, List<SeatWithPrice>> seats;
}

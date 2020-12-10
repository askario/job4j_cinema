package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.persistence.PsqlStore;
import ru.job4j.cinema.persistence.Store;

import java.util.Optional;

public class HallService {
    private final Store store = PsqlStore.instOf();

    private static final class Lazy {
        private static final HallService INST = new HallService();
    }

    public static HallService instOf() {
        return HallService.Lazy.INST;
    }

    public Optional<Hall> getLastHall() {
        return store.getLastHall();
    }

    public void update(Hall hall) {
        store.updateHall(hall);
    }
}

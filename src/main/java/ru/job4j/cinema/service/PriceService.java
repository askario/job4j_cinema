package ru.job4j.cinema.service;

import java.util.Random;

public class PriceService {
    private static final class Lazy {
        private static final PriceService INST = new PriceService();
    }

    public static PriceService instOf() {
        return PriceService.Lazy.INST;
    }

    public int getRandomPrice() {
        return new Random().nextInt(900 - 350) + 350;
    }
}

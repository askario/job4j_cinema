package ru.job4j.cinema.service;


import lombok.val;
import ru.job4j.cinema.model.Order;
import ru.job4j.cinema.model.dto.booking.BookingRequest;
import ru.job4j.cinema.persistence.PsqlStore;
import ru.job4j.cinema.persistence.Store;

public class SeatService {
    private final AccountService accountService = AccountService.instOf();
    private final SessionService sessionService = SessionService.instOf();
    private final Store store = PsqlStore.instOf();

    private static final class Lazy {
        private static final SeatService INST = new SeatService();
    }

    public static SeatService instOf() {
        return SeatService.Lazy.INST;
    }

    public void proceed(BookingRequest bookingRequest) {
        val account = accountService.getAccountByPhone(bookingRequest.getAccount().getPhone())
                .orElseGet(() -> accountService.createAccount(bookingRequest.getAccount()));

        store.create(Order.builder()
                .accountId(account.getId())
                .hallId(bookingRequest.getHall())
                .sessionId(bookingRequest.getSession())
                .seatId(bookingRequest.getId())
                .price(bookingRequest.getPrice())
                .build());
    }
}

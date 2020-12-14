package ru.job4j.cinema.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import ru.job4j.cinema.model.*;
import ru.job4j.cinema.persistence.PsqlStore;
import ru.job4j.cinema.persistence.Store;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SessionService {
    private final Store store = PsqlStore.instOf();
    private final PriceService priceService = PriceService.instOf();

    private static final class Lazy {
        private static final SessionService INST = new SessionService();
    }

    public static SessionService instOf() {
        return SessionService.Lazy.INST;
    }

    public SessionState getSession(int id) {
        val session = (Session) store.getSession(id).orElse(new Session());
        val hall = (Hall) store.getHall(session.getHallId()).orElse(new Hall());

        val occupiedSeats = (List<UniqueKey>) store.getSessionOrders(id).stream().map(order -> UniqueKey.from((Order) order)).collect(Collectors.toList());
        val seats = (List<Seat>) store.getSeatsByHall(hall.getId());

        val seatsWithPrice = seats.stream().map(toSeatWithPrice(id, occupiedSeats)).collect(Collectors.groupingBy(SeatWithPrice::getRow));

        return SessionState.builder()
                .session(session)
                .hall(hall)
                .seats(seatsWithPrice)
                .build();
    }

    private Function<Seat, SeatWithPrice> toSeatWithPrice(int sessionId, List<UniqueKey> occupiedSeats) {
        return seat -> SeatWithPrice.builder()
                .sessionId(sessionId)
                .hallId(seat.getHallId())
                .id(seat.getId())
                .place(seat.getPlace())
                .row(seat.getRow())
                .price(priceService.getRandomPrice())
                .isOccupied(occupiedSeats.stream().anyMatch(os -> os.getSessionId() == sessionId && os.getSeatId() == seat.getId()))
                .build();
    }

    @Getter
    @AllArgsConstructor
    private static class UniqueKey {
        private final int sessionId;
        private final int seatId;

        public static UniqueKey from(Order order) {
            return new UniqueKey(order.getSessionId(), order.getSeatId());
        }
    }
}


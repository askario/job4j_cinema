package ru.job4j.cinema.persistence;

import ru.job4j.cinema.model.*;

import java.util.List;
import java.util.Optional;

public interface Store<T> {
    Account createAccount(Account account);

    Optional<Account> getAccountByPhone(String phone);

    Optional<Session> getSession(int id);

    Optional<Hall> getHall(int id);

    List<Seat> getSeatsByHall(int hallId);

    Order create(Order order);

    List<Order> getSessionOrders(int sessionId);
}

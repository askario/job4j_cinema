package ru.job4j.cinema.persistence;

import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Hall;

import java.util.Optional;

public interface Store {
    Optional<Hall> getLastHall();

    void updateHall(Hall hall);

    Account createAccount(Account account);

    Optional<Account> getAccountByPhone(String phone);
}

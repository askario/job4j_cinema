package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.persistence.PsqlStore;
import ru.job4j.cinema.persistence.Store;

import java.util.Optional;

public class AccountService {
    private final Store store = PsqlStore.instOf();

    private static final class Lazy {
        private static final AccountService INST = new AccountService();
    }

    public static AccountService instOf() {
        return AccountService.Lazy.INST;
    }

    public Optional<Account> getAccountByPhone(String phone) {
        return store.getAccountByPhone(phone);
    }

    public Account createAccount(Account account) {
        return store.createAccount(account);
    }
}
